import "./ReportLec.css";
import { async } from "@firebase/util";
import React, { useState, useEffect, useCallback } from "react";
import { Link } from "react-router-dom";
import { UserAuth } from "../../context/AuthContext";

const ReportLec = () => {
    const { logOut, user } = UserAuth();

  const handleSignOut = async () => {
    try {
      await logOut();
    } catch (error) {
      console.log(error);
    }
  }

  const onReportClick = useCallback(() => {
    window.open('https://forms.gle/fCCNqjzx7UHx5X8Y6')
  })

  return (
    <div className="rereportlec">
      <div className="renavbar1">
        <div className="refpt1">
          <div className="refpt-inner" />
          <div className="rerectangle-div" />
          <div className="reframe">
            <div className="reexam-schedule1">
              Exam schedule management system
            </div>
            <div className="refpt-university1">FPT UNIVERSITY</div>
          </div>
        </div>
        <button className="reavatar1">
          <img className="reavt-icon" alt="" src={user?.photoURL} referrerPolicy="no-referrer" />
          <div className="restudent-name">
            <p className="restudent">{user?.displayName}</p>
          </div>
        </button>
      </div>
      <div className="rerow">
        <div className="remenu">
          <button className="reuser-profile1">
            <img
              className="reprofile-image-icon1"
              alt=""
              src={user?.photoURL} referrerPolicy="no-referrer"
            />
            <div className="rename-group">
              <b className="rename2">{user?.displayName}</b>
              <div className="retittle1">{user?.email}</div>
            </div>
          </button>
          <div className="reoptions1">
            <div className="reexams-group">
              <Link className="reexams2" to={"/examscheduleLec"}>
                <div className="reexams-child">
                  <div className="reicon-container">
                    <img
                      className="rehomeoutline-icon"
                      alt=""
                      src="/icon21.svg"
                    />
                    <div className="reexams3">Exams</div>
                  </div>
                </div>
              </Link>
              <Link className="reregister" to={"/profileLe"}>
                <div className="rehomeoutline-parent">
                  <img
                    className="rehomeoutline-icon"
                    alt=""
                    src="/homeoutline11.svg"
                  />
                  <div className="reprofile4">Profile</div>
                </div>
              </Link>
              <Link className="reregister" to={"/registerLec"}>
                <div className="rehomeoutline-parent">
                  <img
                    className="reupward-trend-icon"
                    alt=""
                    src="/upward-trend1111.svg"
                  />
                  <div className="reregister1">Register</div>
                </div>
              </Link>
              <Link className="resalary" to={"/salary"}>
                <div className="reexams-child">
                  <div className="reicon-container">
                    <img
                      className="rehomeoutline-icon"
                      alt=""
                      src="/fidollarsign.svg"
                    />
                    <div className="reprofile4">Salary</div>
                  </div>
                </div>
              </Link>
              <button className="rereports2">
                <div className="reexams-child">
                  <div className="rereports-parent">
                    <img className="rereports-icon" alt="" src="/reports.svg" />
                    <div className="rereports3">Reports</div>
                    <div className="reframe-item" />
                  </div>
                </div>
              </button>
            </div>
          </div>
          <button onClick={handleSignOut} className="relogout3">
            <div className="relogout-container">
              <b className="relogout4">
                <p className="restudent">Logout</p>
              </b>
            </div>
          </button>
        </div>
        <div className="rebody">
          <b className="rereports4">Reports</b>
          <div className="rebody1">
            <div className="resorry-for-the">Sorry for the inconvenience</div>
            <div className="replease-let-us-know-about-the-t-wrapper">
              <div className="replease-let-us">
                Please let us know about the trouble you are facing, so that we
                can assist you.
              </div>
            </div>
          </div>
          <img className="reimage-2-icon" alt="" src="/image-2@2x.png" />
          <Link className="rebtn-report" to= {"https://docs.google.com/forms/d/e/1FAIpQLSfP_FU4ybNTlmokrrryIzmeUzwU_8O7ConH7upMyXYZjKt4zQ/viewform"}>
            <img className="revector-icon" alt="" src="/vector.svg" />
          </Link>
        </div>
      </div>
      <div className="rebot">
        <div className="rereport2">
          <button className="rereport-item" onClick={onReportClick}/>
          <div className="rereport3">Report</div>
          <div className="reimage-1-container">
            <img className="reimage-1-icon1" alt="" src="/image-111@2x.png" />
          </div>
        </div>
        <img className="relogofpt-icon" alt="" src="/logolong-111@2x.png" />
      </div>
    </div>
  );
};

export default ReportLec;
