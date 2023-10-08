from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.chrome.options import Options
from selenium.webdriver.common.keys import Keys
import re
import time
#from pyvirtualdisplay import Display

def getStudentData(id, pw):
    #display = Display(visible=0, size=(1920, 4000))
    #display.start()
    options = Options()
    options.add_argument('--headless')
    options.add_argument('window-size=1920,4000')


    # Chrome WebDriver 시작
    driver = webdriver.Chrome(options=options)

    # 로그인 페이지로 이동
    driver.get("https://tis.anyang.ac.kr/index.jsp")

    # id = '2018E7088'
    # pw = 'sK35364478@'
    # id = '2019U1132'
    # pw = '!@#atlantis771'

    # 아이디와 비밀번호 입력
    driver.find_element(By.CSS_SELECTOR, "input[name='id']").send_keys(id)
    driver.find_element(By.CSS_SELECTOR, "input[name='password']").send_keys(pw)

    # 로그인 버튼 클릭
    driver.find_element(By.CSS_SELECTOR, ".login_button > a").click()

    # 로그인 성공 여부 확인 후 결과 출력

    try:
        if "로그인" in driver.title:
            print("로그인 실패")
        else:
            print("로그인 성공")

            # 메인 페이지로 이동
            driver.get("https://tis.anyang.ac.kr/main.do")

            # 4초간 기다림
            time.sleep(4)

            # 졸업자가진단 버튼 클릭
            driver.find_element(By.CSS_SELECTOR, "[style*='user-select'][id$='body_gridrow_5']").click()
            driver.find_element(By.CSS_SELECTOR, "[style*='user-select'][id$='gridrow_9_cell_9_0_controltree']").click()

            # 4초간 기다림
            time.sleep(4)

            totalDict = {}
            for i in range(5): # 기준학점,인정학점,취득학점,잔여학점,수강신청학점
                subDict = {}
                category = driver.find_element(By.XPATH, "//*[@id=\"mainframe_childframe_form_mainContentDiv_workDiv_WINB010705_INFODIV01_Grid01_body_gridrow_0_cell_0_{}\"]".format(13*i + 0)).text
                subDict['교양필수'] = driver.find_element(By.XPATH, "//*[@id=\"mainframe_childframe_form_mainContentDiv_workDiv_WINB010705_INFODIV01_Grid01_body_gridrow_0_cell_0_{}\"]".format(13*i + 1)).text
                subDict['교양선택'] = driver.find_element(By.XPATH, "//*[@id=\"mainframe_childframe_form_mainContentDiv_workDiv_WINB010705_INFODIV01_Grid01_body_gridrow_0_cell_0_{}\"]".format(13*i + 2)).text
                subDict['자유선택'] = driver.find_element(By.XPATH, "//*[@id=\"mainframe_childframe_form_mainContentDiv_workDiv_WINB010705_INFODIV01_Grid01_body_gridrow_0_cell_0_{}\"]".format(13*i + 3)).text
                subDict['주전공'] = driver.find_element(By.XPATH, "//*[@id=\"mainframe_childframe_form_mainContentDiv_workDiv_WINB010705_INFODIV01_Grid01_body_gridrow_0_cell_0_{}\"]".format(13*i + 4)).text
                subDict['복수전공'] = driver.find_element(By.XPATH, "//*[@id=\"mainframe_childframe_form_mainContentDiv_workDiv_WINB010705_INFODIV01_Grid01_body_gridrow_0_cell_0_{}\"]".format(13*i + 5)).text
                subDict['연계전공'] = driver.find_element(By.XPATH, "//*[@id=\"mainframe_childframe_form_mainContentDiv_workDiv_WINB010705_INFODIV01_Grid01_body_gridrow_0_cell_0_{}\"]".format(13*i + 6)).text
                subDict['부전공'] = driver.find_element(By.XPATH, "//*[@id=\"mainframe_childframe_form_mainContentDiv_workDiv_WINB010705_INFODIV01_Grid01_body_gridrow_0_cell_0_{}\"]".format(13*i + 7)).text
                subDict['다전공'] = driver.find_element(By.XPATH, "//*[@id=\"mainframe_childframe_form_mainContentDiv_workDiv_WINB010705_INFODIV01_Grid01_body_gridrow_0_cell_0_{}\"]".format(13*i + 8)).text
                subDict['자기설계'] = driver.find_element(By.XPATH, "//*[@id=\"mainframe_childframe_form_mainContentDiv_workDiv_WINB010705_INFODIV01_Grid01_body_gridrow_0_cell_0_{}\"]".format(13*i + 9)).text
                subDict['교직'] = driver.find_element(By.XPATH, "//*[@id=\"mainframe_childframe_form_mainContentDiv_workDiv_WINB010705_INFODIV01_Grid01_body_gridrow_0_cell_0_{}\"]".format(13*i + 10)).text
                subDict['채플'] = driver.find_element(By.XPATH, "//*[@id=\"mainframe_childframe_form_mainContentDiv_workDiv_WINB010705_INFODIV01_Grid01_body_gridrow_0_cell_0_{}\"]".format(13*i + 11)).text
                subDict['졸업학점'] = driver.find_element(By.XPATH, "//*[@id=\"mainframe_childframe_form_mainContentDiv_workDiv_WINB010705_INFODIV01_Grid01_body_gridrow_0_cell_0_{}\"]".format(13*i + 12)).text
                totalDict[category] = subDict

            ################## 학점 이수 현황 [필요항목: 취득, 잔여, 전공, 교양, 졸업] ##################
            creditStatusDict = {}

            # 1. 취득학점
            creditStatusDict['total_current'] = int(totalDict['취득학점']['졸업학점'])
            # 2. 잔여학점
            creditStatusDict['total_remain'] = int(totalDict['잔여학점']['졸업학점'])
            # 3. 전공학점
            creditStatusDict['major_current'] = int(totalDict['취득학점']['주전공']) + int(totalDict['취득학점']['복수전공']) + int(totalDict['취득학점']['연계전공']) + int(totalDict['취득학점']['부전공']) + int(totalDict['취득학점']['다전공'])
            # 4. 교양학점
            creditStatusDict['general_current'] = int(totalDict['취득학점']['교양필수']) + int(totalDict['취득학점']['교양선택']) + int(totalDict['취득학점']['자유선택']) + int(totalDict['취득학점']['채플'])
            # 5. 기준학점
            creditStatusDict['graduate_base'] = int(totalDict['기준학점']['졸업학점'])


            ################## 등록학기 및 채플 이수 현황 [필요항목 : ] ##################  ['구분', '기준학기 수', '등록/이수학기 수', '등록', '8', '8', '채플이수', '4', '4']
            chapelStatusStrList = driver.find_element(By.XPATH, '//*[@id="mainframe_childframe_form_mainContentDiv_workDiv_WINB010705_INFODIV01_Grid02"]').text.split('\n')
            chapelStatusDict = {}
            chapelStatusDict['register'] = [chapelStatusStrList[4], chapelStatusStrList[5]]
            chapelStatusDict['complete'] = [chapelStatusStrList[7], chapelStatusStrList[8]]
            
            ################## 졸업인증 ################## ['구분', '통과여부', '통과년도-학기', '미통과', '-']
            graduateResult = driver.find_element(By.XPATH, '//*[@id="mainframe_childframe_form_mainContentDiv_workDiv_WINB010705_INFODIV01_Grid06"]').text.split('\n')[-2]
            
            ################## 필수과묵 이수현황 ################## ['이수구분', '학수번호', '과목명', '이수여부', '대체과목', '비고', '교양필수', 'AA1072', '기독교개론', '이수', '교양필수', 'AA3027', 'ESP:Critical Thinking', '이수']
            requiredSubjectsList = []
            for row in [0,1,2,3,4,5,6,7,8,9,0]:
                requiredSubjects = []
                try:
                    for col in range(4):
                        if col != 1:
                            requiredSubjects.append(driver.find_element(By.XPATH, '//*[@id="mainframe_childframe_form_mainContentDiv_workDiv_WINB010705_INFODIV01_Grid04_body_gridrow_{}_cell_{}_{}GridCellTextContainerElement"]'.format(row, row, col)).text)
                    driver.find_element(By.XPATH, '//*[@id="mainframe_childframe_form_mainContentDiv_workDiv_WINB010705_INFODIV01_Grid04_body_gridrow_{}_cell_{}_{}GridCellTextContainerElement"]'.format(row, row, 0)).click()
                    requiredSubjectsList.append(requiredSubjects)
                except:
                    break

            ################## 교양선택 역량별 이수현황 ################## ['역량명', '의사소통', '글로벌', '문제해결', '인성영성', '리더십', '융합실무', '이수과목', '2(0)', '2(0)', '7(0)', '2(1)', '1(0)', '0(0)']
            generalSubjectList = driver.find_element(By.XPATH, '//*[@id="mainframe_childframe_form_mainContentDiv_workDiv_WINB010705_INFODIV01_Grid03"]').text.split('\n')[8:]
            
            # Temp Variables
            subjectNameList = ['의사소통', '글로벌', '문제해결', '인성영성', '리더십', '융합실무']
            generalSubjecResultStr = "아래의 역량이 더 필요합니다."
            completeSubject = 0
            
            for i, subjectStr in enumerate(generalSubjectList):
                numbers = re.findall(r'\d+', subjectStr)
                if(int(numbers[0]) < 2):
                    generalSubjecResultStr = generalSubjecResultStr + '\n-' + subjectNameList[i]
                else:
                    completeSubject = completeSubject + 1
            if(completeSubject >= 3):
                generalSubjecResultStr = "모든 역량을 이수하셨습니다."


            # 이름
            stdName = driver.find_element(By.ID, 'mainframe_childframe_form_mainContentDiv_workDiv_WINB010705_COMMONDIV01_INFODIV01_D_NAME_input').get_attribute("value")
            # 학번
            stdId = driver.find_element(By.ID, 'mainframe_childframe_form_mainContentDiv_workDiv_WINB010705_COMMONDIV01_INFODIV01_D_HAKBEON_input').get_attribute("value")
            # 학년
            stdLevel = driver.find_element(By.ID, 'mainframe_childframe_form_mainContentDiv_workDiv_WINB010705_COMMONDIV01_INFODIV01_D_HAKNYEON_input').get_attribute("value")
            # 학과
            stdDepart = driver.find_element(By.ID, 'mainframe_childframe_form_mainContentDiv_workDiv_WINB010705_COMMONDIV01_INFODIV01_D_JEONGONG_NM_input').get_attribute("value")

            retDict = {}
            retDict['creditStatus'] = creditStatusDict
            retDict['chapel'] = chapelStatusDict
            retDict['graduateResult'] = graduateResult
            retDict['requiredSubjects'] = requiredSubjectsList
            retDict['generalSubject'] = generalSubjectList
            retDict['generalSubjectResult'] = generalSubjecResultStr
            retDict['stdName'] = stdName
            retDict['stdId'] = stdId
            retDict['stdLevel'] = stdLevel
            retDict['stdDepart'] = stdDepart

            # # 의사소통
            # driver.find_element(By.XPATH, '//*[@id="mainframe_childframe_form_mainContentDiv_workDiv_WINB010705_INFODIV01_Grid03_body_gridrow_0_cell_0_1_controlexpand"]/div').click()
            # time.sleep(3)
            # print(driver.find_element(By.XPATH, '//*[@id="mainframe_childframe_hg_4050610_p_form_PopupForm_DG_GRID01"]').text.split('\n'))
            # driver.find_element(By.XPATH, '//*[@id="mainframe_childframe_hg_4050610_p_form_PopupForm_POPUPBOTTOMDIV01_ButtonCloseTextBoxElement"]/div').click()

            # # 글로벌
            # driver.find_element(By.XPATH, '//*[@id="mainframe_childframe_form_mainContentDiv_workDiv_WINB010705_INFODIV01_Grid03_body_gridrow_0_cell_0_2_controlexpand"]/div').click()
            # time.sleep(3)
            # print(driver.find_element(By.XPATH, '//*[@id="mainframe_childframe_hg_4050610_p_form_PopupForm_DG_GRID01"]').text.split('\n'))
            # driver.find_element(By.XPATH, '//*[@id="mainframe_childframe_hg_4050610_p_form_PopupForm_POPUPBOTTOMDIV01_ButtonCloseTextBoxElement"]/div').click()

            # # 문제 해결
            # driver.find_element(By.XPATH, '//*[@id="mainframe_childframe_form_mainContentDiv_workDiv_WINB010705_INFODIV01_Grid03_body_gridrow_0_cell_0_3_controlexpand"]/div').click()
            # time.sleep(3)
            # print(driver.find_element(By.XPATH, '//*[@id="mainframe_childframe_hg_4050610_p_form_PopupForm_DG_GRID01"]').text.split('\n'))
            # driver.find_element(By.XPATH, '//*[@id="mainframe_childframe_hg_4050610_p_form_PopupForm_POPUPBOTTOMDIV01_ButtonCloseTextBoxElement"]/div').click()

            # # 인성영성
            # driver.find_element(By.XPATH, '//*[@id="mainframe_childframe_form_mainContentDiv_workDiv_WINB010705_INFODIV01_Grid03_body_gridrow_0_cell_0_4_controlexpand"]/div').click()
            # time.sleep(3)
            # print(driver.find_element(By.XPATH, '//*[@id="mainframe_childframe_hg_4050610_p_form_PopupForm_DG_GRID01"]').text.split('\n'))
            # driver.find_element(By.XPATH, '//*[@id="mainframe_childframe_hg_4050610_p_form_PopupForm_POPUPBOTTOMDIV01_ButtonCloseTextBoxElement"]/div').click()

            # # 리더십
            # driver.find_element(By.XPATH, '//*[@id="mainframe_childframe_form_mainContentDiv_workDiv_WINB010705_INFODIV01_Grid03_body_gridrow_0_cell_0_5_controlexpand"]/div').click()
            # time.sleep(3)
            # print(driver.find_element(By.XPATH, '//*[@id="mainframe_childframe_hg_4050610_p_form_PopupForm_DG_GRID01"]').text.split('\n'))
            # driver.find_element(By.XPATH, '//*[@id="mainframe_childframe_hg_4050610_p_form_PopupForm_POPUPBOTTOMDIV01_ButtonCloseTextBoxElement"]/div').click()

            # # 융합실무
            # driver.find_element(By.XPATH, '//*[@id="mainframe_childframe_form_mainContentDiv_workDiv_WINB010705_INFODIV01_Grid03_body_gridrow_0_cell_0_6_controlexpand"]/div').click()
            # time.sleep(3)
            # print(driver.find_element(By.XPATH, '//*[@id="mainframe_childframe_hg_4050610_p_form_PopupForm_DG_GRID01"]').text.split('\n'))
            # driver.find_element(By.XPATH, '//*[@id="mainframe_childframe_hg_4050610_p_form_PopupForm_POPUPBOTTOMDIV01_ButtonCloseTextBoxElement"]/div').click()
    except Exception as e:
        print(e)
        driver.quit()
        return False

    driver.quit()
    return retDict

if __name__ == '__main__':
    print(getStudentData('2019U1132', '!@#atlantis771'))
