import { async } from "@firebase/util";
import React, { useState, useEffect, useCallback } from "react";
import { Link } from "react-router-dom";
import { UserAuth } from "../../context/AuthContext";
import "./ProStudent_Desktop.css";

const ProStudent_Desktop = () => {
  const { logOut, user } = UserAuth();

  const handleSignOut = async () => {
    try {
      await logOut();
    } catch (error) {
      console.log(error);
    }
  }

  const [studentData, setStudentData] = useState(null);
  const [loading, setLoading] = useState(true);

  console.log(localStorage.getItem("email"));
  const disable = localStorage.getItem("email");
  useEffect(() => {

    if (disable) {
      fetch(`http://localhost:8888/profileStudent?email=${disable}`)
        .then(response => response.json())
        .then(data => {
          setStudentData(data);
          setLoading(false);
        })
        .catch(error => {
          setLoading(false);
        });
    }

  }, [disable]);


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
    window.open('https://forms.gle/fCCNqjzx7UHx5X8Y6');
  }, []);

  //logout => redirect login page
  const onLogoutBtnClick = useCallback(() => {
    window.location.href('http://localhost:3000/');
  }, []);

  const onReportclick = useCallback(() => {
    window.open('https://forms.gle/fCCNqjzx7UHx5X8Y6')
  })

  return (
    <div className="vprostudent">
      <div className="vnavbar">
        <div className="vfpt">
          <div className="vfpt-child" />
          <div className="vfpt-item" />
          <div className="vframe">
            <div className="vexam-schedule">Exam schedule management system</div>
            <div className="vfpt-university">FPT UNIVERSITY</div>
          </div>
        </div>
        <button className="vavatar" onClick={onAvatarProClick}>
          <img className="vavt-icon" alt="" src={user?.photoURL} referrerPolicy="no-referrer" />
          <div className="vstudent-name">
            <p className="vstudent">{user?.displayName}</p>
          </div>
        </button>
      </div>
      <div className="vrow">
        <div className="vmenu">
          <button className="vuser-profile" onClick={onUserProClick}>
            <img
              className="vprofile-image-icon"
              alt=""
              src={user?.photoURL}
              referrerPolicy="no-referrer"
            />
            <div className="vname-parent">
              <b className="vname1">{user?.displayName}</b>
              <div className="vtittle">{user?.email}</div>
            </div>
          </button>
          <div className="voptions">
            <div className="vexams-parent">
              <Link className="vexams" to={"/examscheduleStu"}>
                <div className="vexams-inner">
                  <div className="vicon-parent">
                    <img className="vicon" alt="" src="/icon.svg" />
                    <div className="vexams1">Exams</div>
                  </div>
                </div>
              </Link>
              {/* <button className="profile" onClick={onProStuClick}>
                <div className="exams-inner">
                  <div className="template-parent">
                    <img className="template-icon" alt="" src="/template.svg" />
                    <div className="profile1">Profile</div>
                    <div className="frame-child" />
                  </div>
                </div>
              </button> */}


                <Link className="vregister" to="/profileSt">
                  <div className="vregister-inner">
                    <div className="vicon-container">
                      <img className="vicon2" alt="" src="/homeoutline.svg" />
                      <div className="vexams2">Profile</div>
                      <div className="vframe-item" />
                    </div>
                  </div>
                </Link>
                <Link className="vreports" to={"/reportSt"}>
                  <div className="vexams-inner">
                    <div className="vicon-group">
                      <img className="vicon1" alt="" src="/icon1.svg" />
                      <div className="vreports1">Reports</div>
                    </div>
                  </div>
                </Link>
              </div>
            </div>
            {/* <button className="logout" onClick={onLogoutBtnClick}>
            <div className="logout-wrapper">
              <b className="logout1">
                <span className="logout-txt">
                  <p className="student">Logout</p>
                </span>
              </b>
            </div>
          </button> */}
            <button onClick={handleSignOut} className="vlogout">
              Logout
            </button>
          </div>
          <div className="vbody2">
            <b className="vprofile2">Profile</b>
            <div className="vstudent-id">Student ID:</div>
            <div className="vse173049-wrapper">
              <div className="vse173049">{loading ? 'Loading...' : studentData?.id}</div>
            </div>
            <div className="vfull-name">Full Name:</div>
            <div className="vse173049-wrapper">
              <div className="von-nh-tn">{loading ? 'Loading...' : studentData?.name}</div>
            </div>
            <div className="vstudent-id">Email:</div>
            <div className="vtinddse173049fpteduvn-wrapper">
              <div className="vtinddse173049fpteduvn">
                {loading ? 'Loading...' : studentData?.email}
              </div>
            </div>
          </div>
        </div>
        <div className="vbot">
          <div className="vreport">
            <button className="vreport-child" onClick={onReportclick} />
            <div className="vreport1">Report</div>
            <div className="vimage-1-wrapper">
              <img className="vimage-1-icon" alt="" src="/image-1@2x.png" />
            </div>
          </div>
          <img className="vlogofpt-icon" alt="" src="/logofpt@2x.png" />
        </div>
      </div>
      );
};

      export default ProStudent_Desktop;