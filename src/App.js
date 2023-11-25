import React, { useEffect, useState } from "react";
import { Routes, Route, useNavigationType, useLocation } from "react-router-dom";
import { AuthContextProvider, UserAuth } from "./context/AuthContext";
import Desktop_login from "./pages/LoginPage/Desktop_login";
import Moobile_login from "./pages/LoginPage/Mobile_login";
import ProStudent_Desktop from "./pages/ProStudent/ProStudent_Desktop";
import ProLec_Desktop from "./pages/ProLec/ProLec_Desktop";
import Proctected from "./components/Protected";
import SalaryLec_Desktop from "./pages/SalaryLec/SalaryLec_Desktop";
import CreateDataEx_Desktop from "./pages/CreateDataEx/CreateDataEx_Desktop";
import CreaExam from "./pages/CreateExamEx/CreaExam";
import ExscheduleLec from "./pages/ExScheLec/ExscheduleLec";
import Exschedulestu from "./pages/ExScheStu/Exschedulestu";
import ReportLec from "./pages/ReportLec/ReportLec";
import ReportSt from "./pages/ReportSt/ReportSt";

import EditExamina from "./pages/EditExami/EditExamina";
import RegisLec from "./pages/RegistLec/RegisLec";
import ExamExamina from "./pages/ExScheExam/ExamExamina";
import ExportDataEx from "./pages/ExportDataEx/ExportDataEx"
import ExReaEx from "./pages/ExReaEx/ExReaEx";


function App() {
  const action = useNavigationType();
  const location = useLocation();
  const pathname = location.pathname;

  const [isMobile, setIsMobile] = useState(window.innerWidth <= 500);

  useEffect(() => {
    const handleResize = () => {
      setIsMobile(window.innerWidth <= 500);
    };

    // Attach the event listener
    window.addEventListener("resize", handleResize);

    // Detach the event listener on component unmount
    return () => {
      window.removeEventListener("resize", handleResize);
    };
  }, []); // Empty dependency array ensures the effect runs once after initial render

  useEffect(() => {
    if (action !== "POP") {
      window.scrollTo(0, 0);
    }
  }, [action, pathname]);

  useEffect(() => {
    let title = "";
    let metaDescription = "";

    switch (pathname) {
      case "/":
        title = "";
        metaDescription = "";
        break;
      // Add more cases for other routes if needed
      default:
        break;
    }

    if (title) {
      document.title = title;
    }

    if (metaDescription) {
      const metaDescriptionTag = document.querySelector(
        'head > meta[name="description"]'
      );
      if (metaDescriptionTag) {
        metaDescriptionTag.content = metaDescription;
      }
    }
  }, [pathname]);

  //Conditionally render the appropriate component based on screen width



  return (
    <div>
      <AuthContextProvider>
        <Routes>
          <Route path="/" element={isMobile ? <Moobile_login /> : <Desktop_login />} />
          < Route path="/salary" element={
            <Proctected>
              <SalaryLec_Desktop />
            </Proctected>
          }
          />

          < Route path="/createDataEx" element={
            <Proctected>
              <CreateDataEx_Desktop />
            </Proctected>
          }
          />

          < Route path="/registerLec" element={
            <Proctected>
              < RegisLec />
            </Proctected>
          }
          />

          < Route path="/examscheduleLec" element={
            <Proctected>
              <ExscheduleLec />
            </Proctected>
          }
          />

          < Route path="/examscheduleStu" element={
            <Proctected>
              <Exschedulestu />
            </Proctected>
          }
          />


          < Route path="/editExamina" element={
            <Proctected>
              <EditExamina />
            </Proctected>
          }
          />

          < Route path="/profileSt" element={
            <Proctected>
              <ProStudent_Desktop />
            </Proctected>
          }
          />

          < Route path="/exscheduleExami" element={
            <Proctected>
              <ExamExamina />
            </Proctected>
          }
          />

          < Route path="/exReasonExami" element={
            <Proctected>
              <ExReaEx />
            </Proctected>
          }
          />

          < Route path="/profileLe" element={
            <Proctected>
              <ProLec_Desktop />
            </Proctected>
          }
          />
          < Route path="/createExamEx" element={
            <Proctected>
              <CreaExam />
            </Proctected>
          }
          />

          < Route path="/exportData" element={
            // <Proctected>
              <ExportDataEx />
            // </Proctected>
          }
          />

          < Route path="/reportLec" element={
            <Proctected>
              <ReportLec />
            </Proctected>
          }
          />
          < Route path="/reportSt" element={
            <Proctected>
              <ReportSt />
            </Proctected>
          }
          />

          {/* <Route path="/profile" element={isMobile ? <ProStudentMobile /> : (window.innerWidth >= 840 ? <ProStudentTablet /> : <ProStudentDesktop />)} /> */}
        </Routes>
      </AuthContextProvider>
    </div >
  );

}
export default App;