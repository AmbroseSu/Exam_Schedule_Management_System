import { async } from "@firebase/util";
import React, { useState, useEffect, useCallback } from "react"; 
import { Link } from "react-router-dom";
import { UserAuth } from "../../context/AuthContext";
import "./ProLec_Desktop.css";

const ProLec_Desktop = () => {
  const { logOut, user } = UserAuth();

  const handleSignOut = async() => {
    try{
      await logOut();
    }catch(error){
      console.log(error);
    }
  }

  const [lecturerData, setLecturerData] = useState(null);
  const [loading, setLoading] = useState(true);

  console.log(localStorage.getItem("email"));
  const disable = localStorage.getItem("email");

  useEffect(() => {
    fetch(`http://localhost:8888/profileLecturer?email=${disable}`)
    .then(response => response.json())
    .then(data => {
      setLecturerData(data);
      setLoading(false);
    })
    .catch(error => {
      setLoading(false);
    });
  }, []);

  //All links

  const onAvatarProClick = useCallback(() => {
    window.location.href('http://localhost:3000/ProStudent');
  }, []);

  const onUserProClick = useCallback(() => {
    window.location.href('http://localhost:3000/ProStudent');
  }, []);

  const onExamStuClick = useCallback(() => {
    window.location.href('http://localhost:3000/ExStudent');
  }, []);
  
  const onProStuClick = useCallback(() => {
    window.location.href('http://localhost:3000/ProStudent');
  }, []);

  //use Google report => change link
  const onReportStuClick = useCallback(() => {
    window.open('http://localhost:3000/ProStudent');
  }, []);

  //logout => redirect login page
  const onReportClick = useCallback(() => {
    window.open('https://forms.gle/fCCNqjzx7UHx5X8Y6');
}, []);

  return (
    <div className="prolecturer">
      <div className="navbar9">
        <div className="fpt9">
          <div className="fpt-child15" />
          <div className="fpt-child16" />
          <div className="frame2">
            <div className="exam-schedule9">
              Exam schedule management system
            </div>
            <div className="fpt-university9">FPT UNIVERSITY</div>
          </div>
        </div>
        <button className="avatar9">
          <img className="avt-icon2" alt="" src={user?.photoURL} referrerPolicy="no-referrer" />
          <div className="student-name2">
            <p className="lecturer8">{user?.displayName}</p>
          </div>
        </button>
      </div>
      <div className="row5">
        <div className="menu5">
          <button className="user-profile9">
            <img
              className="profile-image-icon9"
              alt=""
              src={user?.photoURL} referrerPolicy="no-referrer" 
            />
            <div className="name-parent7">
              <b className="name15">{user?.displayName}</b>
              <div className="tittle9">{user?.email}</div>
            </div>
          </button>
          <div className="options4">
            <div className="exams-parent4">
              <Link className="exams18" to={"/examscheduleLec"}>
                <div className="exams-inner5">
                  <div className="fidollar-sign-container">
                    <img className="icon12" alt="" src="/icon4.svg" />
                    <div className="exams19">Exams</div>
                  </div>
                </div>
              </Link>
              <Link className="profile15" to={"/profileLe"}>
                <div className="exams-inner5">
                  <div className="template-parent10">
                    <img
                      className="template-icon12"
                      alt=""
                      src="/homeoutline.svg"
                    />
                    <div className="profile16">Profile</div>
                    <div className="frame-child24" />
                  </div>
                </div>
              </Link>
              <Link className="exams18" to={"/registerLec"}>
                <div className="exams-inner5">
                  <div className="fidollar-sign-container">
                    <img
                      className="upward-trend-icon8"
                      alt=""
                      src="/upward-trend1.svg"
                    />
                    <div className="exams19">Register</div>
                  </div>
                </div>
              </Link>
              <Link className="exams18" to={"/salary"}>
                <div className="exams-inner5">
                  <div className="fidollar-sign-container">
                    <img className="icon12" alt="" src="/fidollarsign.svg" />
                    <div className="salary16">Salary</div>
                  </div>
                </div>
              </Link>
              <Link className="exams18" to={"/reportLec"}>
                <div className="exams-inner5">
                  <div className="fidollar-sign-container">
                    <img className="icon12" alt="" src="/icon5.svg" />
                    <div className="salary16">Reports</div>
                  </div>
                </div>
              </Link>
            </div>
          </div>
          {/* <button className="logout27">
            <div className="logout-wrapper7">
              <b className="logout28">
                <span className="logout-txt6">
                  <p className="lecturer8">Logout</p>
                </span>
              </b>
            </div>
          </button> */}
          <button onClick={handleSignOut} className="logout27">
            Logout
          </button>
        </div>
        <div className="body6">
          <b className="profile17">Profile</b>
          <div className="lecturer-id1">Lecturer ID:</div>
          <div className="se173049-wrapper1">
            <div className="se1730493">{loading ? 'Loading...' : lecturerData?.id}</div>
          </div>
          <div className="full-name3">Full Name:</div>
          <div className="se173049-wrapper1">
            <div className="on-nh-tn3">{loading ? 'Loading...' : lecturerData?.Name}</div>
          </div>
          <div className="lecturer-id1">Email:</div>
          <div className="tinddse173049fpteduvn-wrapper1">
            <div className="tinddse173049fpteduvn3">
              {loading ? 'Loading...' : lecturerData?.Email}
            </div>
          </div>
          <div className="lecturer-id1">Phone:</div>
          <div className="tinddse173049fpteduvn-wrapper1">
            <div className="tinddse173049fpteduvn3">{loading ? 'Loading...' : lecturerData?.Phone}</div>
          </div>
        </div>
      </div>
      <div className="bot8">
        <div className="report18">
          <button className="report-child6" onClick={onReportClick}/>
          <div className="report19">Report</div>
          <div className="image-1-wrapper7">
            <img className="image-1-icon9" alt="" src="/image-11@2x.png" />
          </div>
        </div>
        <img className="logofpt-icon2" alt="" src="/logolong-11@2x.png" />
      </div>
    </div>
  );
};

export default ProLec_Desktop;