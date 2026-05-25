#!/usr/bin/env bash
set -euo pipefail

# Frontend smoke tests that do NOT install any packages.
# - Runs the existing `npm run build` script
# - Verifies `dist/frontend` exists and contains `index.html` and JS bundles
# Usage: bash tests/frontend_smoke.sh

ROOT_DIR="$(cd "$(dirname "$0")/.." && pwd)"
FRONTEND_DIR="$ROOT_DIR/frontend"
DIST_DIR="$FRONTEND_DIR/dist/frontend"

echo "[smoke] Frontend smoke tests (no installs)"

if [ ! -d "$FRONTEND_DIR" ]; then
  echo "[error] frontend directory not found: $FRONTEND_DIR"
  exit 2
fi

cd "$FRONTEND_DIR"

echo "[smoke] Running build using existing scripts: npm run build"
npm run build --silent

echo "[smoke] Verifying dist directory: $DIST_DIR"
if [ ! -d "$DIST_DIR" ]; then
  echo "[error] Build output not found: $DIST_DIR"
  exit 3
fi

echo "[smoke] Locating index.html inside dist (including subfolders)"
INDEX=$(find "$DIST_DIR" -maxdepth 3 -type f -name index.html | head -n1 || true)
if [ -z "$INDEX" ]; then
  echo "[error] index.html not found in dist (searched up to depth 3)"
  exit 4
fi

echo "[smoke] Using index: $INDEX"
echo "[smoke] Checking index.html content for expected markers"
if ! grep -q "<app-root" "$INDEX" && ! grep -q "<base href" "$INDEX"; then
  echo "[error] index.html does not contain <app-root> or <base href> — sanity check failed"
  exit 5
fi

echo "[smoke] Checking for generated JS bundles anywhere under dist"
js_count=$(find "$DIST_DIR" -type f -name "*.js" | wc -l || true)
if [ "${js_count:-0}" -lt 1 ]; then
  echo "[error] No .js bundles found in $DIST_DIR"
  exit 6
fi

echo "[smoke] Frontend smoke tests PASSED — dist present and looks valid"
exit 0
