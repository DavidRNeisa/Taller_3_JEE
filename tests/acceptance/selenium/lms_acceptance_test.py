from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.chrome.options import Options
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC

BASE_URL = "http://localhost:4200"


def test_lms_course_navigation():
    options = Options()
    options.add_argument("--headless=new")
    options.add_argument("--window-size=1440,900")

    driver = webdriver.Chrome(options=options)
    wait = WebDriverWait(driver, 15)

    try:
      driver.get(BASE_URL)
      wait.until(EC.presence_of_element_located((By.TAG_NAME, "app-root")))

      # Navigate to courses from home page
      courses_link = wait.until(EC.element_to_be_clickable((By.PARTIAL_LINK_TEXT, "Cursos")))
      courses_link.click()
      wait.until(EC.url_contains("/courses"))

      # Enter first course
      action_link = wait.until(EC.element_to_be_clickable((By.PARTIAL_LINK_TEXT, "Entrar al curso")))
      action_link.click()
      wait.until(EC.url_contains("/courses/"))

      # Assert course title and lesson content are visible
      heading = wait.until(EC.presence_of_element_located((By.TAG_NAME, "h1")))
      assert heading.text.strip() != ""

      content_blocks = driver.find_elements(By.CSS_SELECTOR, "article, section")
      assert len(content_blocks) > 0
    finally:
      driver.quit()


if __name__ == "__main__":
    test_lms_course_navigation()
    print("Selenium acceptance smoke: PASSED")
