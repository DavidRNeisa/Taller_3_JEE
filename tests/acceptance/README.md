Acceptance tests (Cypress)

Prerequisites:
- Node.js and npm installed
- From workspace root: `cd frontend` then `npm install` to install frontend deps

Run Cypress (interactive):

```bash
cd frontend
npx cypress open --project ../tests/acceptance/cypress
```

Run Cypress headless:

```bash
cd frontend
npx cypress run --spec "../tests/acceptance/cypress/sample_spec.cy.js"
```

The example spec navigates to the app homepage and checks that main sections load. Adapt selectors to the project components.
