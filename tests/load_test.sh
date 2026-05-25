#!/usr/bin/env bash
# Simple load-test helper: uses `ab` or `wrk` if available, otherwise fallbacks to parallel curl

URL=${1:-http://localhost:8080/api/cursos}
THREADS=${2:-100}
DURATION=${3:-30}

command -v ab >/dev/null 2>&1 && AB=1 || AB=0
command -v wrk >/dev/null 2>&1 && WRK=1 || WRK=0

echo "Target: $URL"
if [ "$AB" -eq 1 ]; then
  echo "Running ApacheBench (ab) with $THREADS requests..."
  ab -n $THREADS -c 10 -s $DURATION $URL
  exit 0
fi

if [ "$WRK" -eq 1 ]; then
  echo "Running wrk with $THREADS threads for $DURATION seconds..."
  wrk -t2 -c100 -d${DURATION}s $URL
  exit 0
fi

echo "No ab/wrk found — fallback to parallel curl (best-effort)." 
# Run N requests in parallel and report simple stats
N=${THREADS}
TMP=$(mktemp)
seq 1 $N | xargs -n1 -P50 -I{} sh -c "curl -s -w '%{time_total}\n' -o /dev/null $URL || echo ERROR" >> $TMP
awk 'BEGIN{sum=0;cnt=0;errs=0} /ERROR/{errs++;next} {sum+=$1;cnt++} END{ if(cnt>0) printf("requests=%d successes=%d errors=%d avg_time=%f\n", cnt+errs, cnt, errs, sum/cnt); else print "no successful requests" }' $TMP
rm -f $TMP
