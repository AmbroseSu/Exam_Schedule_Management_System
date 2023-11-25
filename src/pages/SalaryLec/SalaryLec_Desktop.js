import { async } from "@firebase/util";
import React, { useState, useEffect, useCallback } from "react";
import { Link } from "react-router-dom";
import { UserAuth } from "../../context/AuthContext";
import "./SalaryLec_Desktop.css";

const SalaryLec_Desktop = () => {

  const { logOut, user } = UserAuth();

  const handleSignOut = async () => {
    try {
      await logOut();
    } catch (error) {
      console.log(error);
    }
  }

  const [lecturerData, setLecturerData] = useState([]);
  // const [lecturerData, setLecturerData] = useState(null);
  const [loading, setLoading] = useState(true);

  console.log(localStorage.getItem("email"));
  const disable = localStorage.getItem("email");
  useEffect(() => {
    fetch(`http://localhost:8888/viewexamlecturerchoose?email=${disable}`)
      .then(response => response.json())
      .then(data => {
        setLecturerData(data);
        setLoading(false);
      })
      .catch(error => {
        console.error('Error to call API:', error);
        setLoading(false);
      });
  }, []);

  const [salary, salaryData] = useState([]);
  useEffect(() => {
    fetch(`http://localhost:8888/salary?email=${disable}`)
      .then(response => response.json())
      .then(data => {
        salaryData(data);
        setLoading(false);
      })
      .catch(error => {
        console.error('Error to call API:', error);
        setLoading(false);
      });
  }, []);


  const onReportClick = useCallback(() => {
    window.open('https://forms.gle/fCCNqjzx7UHx5X8Y6')
  })

  return (
    <div className="salary6">
      <div className="navbar6">
        <div className="fpt6">
          <div className="fpt-child9" />
          <div className="fpt-child10" />
          <div className="frame1">
            <div className="exam-schedule6">
              Exam schedule management system
            </div>
            <div className="fpt-university6">FPT UNIVERSITY</div>
          </div>
        </div>
        <button className="avatar6">
          <img className="avt-icon1" alt="" src={user?.photoURL} referrerPolicy="no-referrer" />
          <div className="student-name1">
            <p className="student7">{user?.displayName}</p>
          </div>
        </button>
      </div>
      <div className="row3">
        <div className="menu4">
          <button className="user-profile6">
            <img
              className="profile-image-icon6"
              alt=""
              src={user?.photoURL} referrerPolicy="no-referrer"
            />
            <div className="name-parent4">
              <b className="name10">{user?.displayName}</b>
              <div className="tittle6">{user?.email}</div>
            </div>
          </button>
          <div className="options3">
            <div className="exams-parent1">
              <Link className="exams12" to={"/examscheduleLec"}>
                <div className="exams-inner2">
                  <div className="icon-parent4">
                    <img className="icon6" alt="" src="/icon21.svg" />
                    <div className="exams13">Exams</div>
                  </div>
                </div>
              </Link>
              {/* <button className="profile7">
                <div className="homeoutline-container">
                  <img className="icon6" alt="" src="/homeoutline.svg" />
                  <div className="profile8">Profile</div>
                </div>
              </button> */}
              <Link className="exams12" to={"/profileLe"}>
                <div className="exams-inner2">
                  <div className="icon-parent4">
                    <img className="icon6" alt="" src="/homeoutline.svg" />
                    <div className="exams13">Profile</div>
                  </div>
                </div>
              </Link>
              {/* <button className="profile7">
                <div className="homeoutline-container">
                  <img
                    className="upward-trend-icon5"
                    alt=""
                    src="/upward-trend1.svg"
                  />
                  <div className="register5">Register</div>
                </div>
              </button> */}
              <Link className="exams12" to={"/registerLec"}>
                <div className="exams-inner2">
                  <div className="icon-parent4">
                    <img className="icon6" alt="" src="/upward-trend1.svg" />
                    <div className="exams13">Register</div>
                  </div>
                </div>
              </Link>
              <Link className="salary7" to={"/salary"}>
                <div className="exams-inner2">
                  <div className="template-parent7">
                    <img
                      className="template-icon9"
                      alt=""
                      src="/template11.svg"
                    />
                    <div className="salary8">Salary</div>
                    <div className="frame-child16" />
                  </div>
                </div>
              </Link>
              {/* <button className="exams12">
                <div className="exams-inner2">
                  <div className="icon-parent4">
                    <img className="icon6" alt="" src="/icon31.svg" />
                    <div className="profile8">Reports</div>
                  </div>
                </div>
              </button> */}
              <Link className="exams12" to={"/reportLec"}>
                <div className="exams-inner2">
                  <div className="icon-parent4">
                    <img className="icon6" alt="" src="/icon31.svg" />
                    <div className="exams13">Report</div>
                  </div>
                </div>
              </Link>
            </div>
          </div>
          {/* <button className="logout18">
            <div className="logout-wrapper4">
              <b className="logout19">
                <p className="student7">Logout</p>
              </b>
            </div>
          </button> */}
          <button onClick={handleSignOut} className="logout18">
            Logout
          </button>
        </div>
        <div className="body3">
          <b className="salary9">
            <p className="student7">Salary</p>
          </b>
          <div className="body-inner">
            <div className="date-container">
              <div className="date2">Date</div>
              <div className="date2">Hours</div>
            </div>
          </div>
          {/* <div className="rectangle-parent8">
            <div className="frame-child17" />
            <div className="wrapper11">
              <div className="div14">27/07/2023</div>
            </div>
            <div className="h3010">1h30</div>
          </div>
          <div className="rectangle-parent8">
            <div className="frame-child17" />
            <div className="wrapper11">
              <div className="div14">27/07/2023</div>
            </div>
            <div className="h3010">1h30</div>
          </div>
          <div className="rectangle-parent8">
            <div className="frame-child17" />
            <div className="wrapper11">
              <div className="div14">27/07/2023</div>
            </div>
            <div className="h3010">1h30</div>
          </div>
          <div className="rectangle-parent8">
            <div className="frame-child17" />
            <div className="wrapper11">
              <div className="div14">27/07/2023</div>
            </div>
            <div className="h3010">1h30</div>
          </div>
          <div className="rectangle-parent8">
            <div className="frame-child17" />
            <div className="wrapper11">
              <div className="div14">27/07/2023</div>
            </div>
            <div className="h3010">1h30</div>
          </div> */}

          <div className="bodySala">
            {/* Render danh sách khuôn mẫu */}
            {lecturerData && lecturerData.length > 0 ? (
            lecturerData.map((lecturerData, index) => (
              <div className="rectangle-parent8" key={index}>
                <div className="frame-child17" />
                <div className="wrapper11">
                  <div className="div14">{loading ? 'Loading...' : lecturerData.Date}</div>
                </div>
                <div className="h3010">{loading ? 'Loading...' : lecturerData.Hour}</div>
              </div>
            )))
          : (
            <div>No data available</div>
          )}
          </div>

          <div className="total2">
            <div className="total3">{`Total `}</div>
          </div>
          <div className="icontotal">
            <img className="icon6" alt="" src="/uclock.svg" />
            <div className="totalhours2">
              <div className="div19">
                {loading ? 'Loading...' : salary?.hour}
                {/* 6 */}
                </div>
            </div>
            <img className="icon6" alt="" src="/fichevronsright.svg" />
            <img className="icon6" alt="" src="/umoneywithdrawal.svg" />
            <div className="wrapper16">
              <div className="div20">
                {loading ? 'Loading...' : salary?.salary}
                {/* 180.000 */}
                </div>
            </div>
          </div>
        </div>
      </div>
      <div className="bot5">
        <div className="report12">
          <button className="report-child3" onClick={onReportClick}/>
          <div className="report13">Report</div>
          <div className="image-1-wrapper4">
            <img className="image-1-icon6" alt="" src="/image-111@2x.png" />
          </div>
        </div>
        <img className="logofpt-icon1" alt="" src="/logolong-111@2x.png" />
      </div>
    </div>
  );
};

export default SalaryLec_Desktop;