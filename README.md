## 📊 Excel API 연동
이 프로젝트는 **Excel API**를 활용하여 엑셀 다운로드 기능을 구현한 과정에서 학습한 내용을 정리한 것입니다.<br>
특히, **가독성**과 **유지보수성**을 고려한 메서드 설계를 중심으로 구현 방식을 비교하며 발전시킨 과정을 기록합니다.

<br><br>

## 🖥️ 개발 환경
- **Back-end**: Java
- **Framework**: Spring Boot, Spring Data JPA
- **DB**: PostgreSQL
- **WAS**: Embedded Tomcat 
- **버전 관리**: Git

  <br><br>

## 1. 메서드를 여러 개로 분할 ##
<table>
  <tr>
    <td><img src="https://github.com/user-attachments/assets/5f3bc0e0-22be-4275-afdc-a2a8142ffd1f" alt="image"></td>
    <td><img src="https://github.com/user-attachments/assets/2dbdea06-d66f-46bd-8b70-219bbe480e76" alt="image"></td>
  </tr>
  <tr>
    <td colspan="2" align="center">메서드를 여러개로 분할</td>
  </tr>
</table>

**[설명]**
처음에는 엑셀 다운로드 기능을 구현하면서 테이블이 변경될 때 유연하게 대처할 수 있는 구조를 목표로 설계했습니다.
이를 위해:

* 하드코딩을 지양하고, 데이터베이스 컬럼과 Java 객체 간 매핑을 동적으로 처리하도록 **Reflection**을 활용했습니다.
* 단일 메서드에서 모든 로직을 처리하면 가독성과 유지보수성이 떨어질 수 있어, 메서드를 여러 개로 분할하여 각각의 책임을 명확히 했습니다.

**[문제점]**
그러나 메서드를 과도하게 분할한 결과:

* 메서드 호출이 많아져 로직의 **흐름**을 파악하기 어려웠습니다.
* 가독성이 오히려 저하되는 **부작용**이 발생했습니다


<br><br>

## 2. 가독성을 위한 최소한의 메서드 분할 ##
<table>
  <tr>
    <td><img src="https://github.com/user-attachments/assets/4c18df64-f9ea-4285-bd39-68aeb5c3911c" alt="image"></td>
    <td><img src="https://github.com/user-attachments/assets/db2627a9-20ff-4296-9912-70c099400fb1" alt="image"></td>
  </tr>
  <tr>
    <td colspan="2" align="center">가독성을 위한 최소한의 메서드 분할</td>
  </tr>
</table>

**[설명]**
위의 문제점을 개선하기 위해:
* **메서드 분할**의 적정 수준을 고민하며 **코드를 재구성**했습니다.
* 핵심 로직을 중심으로 **최소한으로 분할**하여, **읽기 쉽고 유지보수에 용이**한 코드로 발전시켰습니다.

**[결과]**
* 필드와 데이터를 유연하게 처리하는 **Reflection** 로직은 유지하되, **불필요한 메서드 분리를 제거**했습니다.
* 이 과정에서 **유동적 데이터 매핑**과 **가독성** 있는 설계 간의 균형을 맞출 수 있었습니다.

<br><br>

<table>
  <tr>
    <td><img src="https://github.com/user-attachments/assets/5247229f-5fee-4824-9440-27818304360a" alt="image"></td>
  </tr>
  <tr>
    <td align="center">bbs Table</td>
  </tr>
  <tr>
    <td><img src="https://github.com/user-attachments/assets/a8bbf89c-17cf-4e8d-a869-5ca8d5c0ece8" alt="image"></td>
  </tr>
  <tr>
    <td align="center">다운로드 받은 Excel</td>
  </tr>
</table>

<br><br>

## 📚 프로젝트 후기

이 프로젝트에서는 단순히 Excel 파일을 생성하고 다운로드하는 기능을 구현했지만, 더 나아가 셀 서식이나 스타일 지정 같은 고급 기능도 추가할 수 있다는 것을 알게 되었습니다.
다음에 업무적으로 Excel 관련 기능을 담당하게 된다면, 이러한 고급 기능들을 활용하여 보다 상세하고 정교한 구현을 시도하고 싶습니다.

