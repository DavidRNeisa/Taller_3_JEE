describe('LMS smoke e2e', () => {
  it('loads homepage and navigates courses', () => {
    cy.visit('http://localhost:4200/')
    cy.contains('Cursos').should('exist')
    cy.get('a').contains('Cursos').click()
    cy.url().should('include', '/courses')
    cy.get('app-course-list').should('exist')
  })
})
