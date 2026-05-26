describe('LMS acceptance flows', () => {
  beforeEach(() => {
    cy.visit('http://localhost:4200/')
  })

  it('AC-UI-01 home renders and allows navigation to courses', () => {
    cy.contains('Cursos').should('exist').click()
    cy.url().should('include', '/courses')
    cy.get('app-course-list').should('exist')
  })

  it('AC-UI-02 user can open a course and view lessons', () => {
    cy.contains('Cursos').click()
    cy.contains('Entrar al curso').first().click({ force: true })
    cy.url().should('match', /\/courses\/[0-9]+/)
    cy.get('h1').should('be.visible')
  })

  it('AC-UI-03 recommendations panel is visible', () => {
    cy.contains('Recomendaciones').should('exist')
    cy.contains(/Siguiente tema sugerido|recomend/i).should('exist')
  })

  it('AC-UI-04 should show backend error feedback if API is unavailable', () => {
    cy.intercept('GET', '**/api/cursos', {
      statusCode: 500,
      body: { error: 'forced by test' }
    }).as('coursesErr')

    cy.reload()
    cy.wait('@coursesErr')
    cy.contains(/error|no se pudo|fall/i).should('exist')
  })
})
