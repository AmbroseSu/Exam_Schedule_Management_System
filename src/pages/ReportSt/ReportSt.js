import "./ReportSt.css";
import { async } from "@firebase/util";
import React, { useState, useEffect, useCallback } from "react";
import { Link } from "react-router-dom";
import { UserAuth } from "../../context/AuthContext";

const ReportSt = () => {
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
    <div className="zxreportstu">
      <div className="zxnavbar1">
        <div className="zxfpt1">
          <div className="zxfpt-inner" />
          <div className="zxrectangle-div" />
          <div className="zxframe">
            <div className="zxexam-schedule1">
              Exam schedule management system
            </div>
            <div className="zxfpt-university1">FPT UNIVERSITY</div>
          </div>
        </div>
        <button className="zxavatar1">
          <img className="zxavt-icon" alt="" src={user?.photoURL} referrerPolicy="no-referrer" />
          <div className="zxstudent-name">
            <p className="zxstudent">{user?.displayName}</p>
          </div>
        </button>
      </div>
      <div className="zxrow">
        <div className="zxmenu">
          <button className="zxuser-profile1">
            <img
              className="zxprofile-image-icon1"
              alt=""
              src={user?.photoURL} referrerPolicy="no-referrer"
            />
            <div className="zxname-group">
              <b className="zxname2">{user?.displayName}</b>
              <div className="zxtittle1">{user?.email}</div>
            </div>
          </button>
          <div className="zxexams-group">
            <Link className="zxexams2" to={"/examscheduleStu"}>
              <div className="zxexams-child">
                <div className="zxicon-container">
                  <img className="zxicon2" alt="" src="/icon2.svg" />
                  <div className="zxexams3">Exams</div>
                </div>
              </div>
            </Link>
            <Link className="zxprofile3" to={"/profileSt"}>
              <div className="zxhomeoutline-parent">
                <img
                  className="zxhomeoutline-icon"
                  alt=""
                  src="/homeoutline.svg"
                />
                <div className="zxprofile4">Profile</div>
              </div>
            </Link>
            <Link className="zxprofile5" to={"/reportSt"}>
              <div className="zxexams-child">
                <div className="zxtemplate-group">
                  <img className="zxtemplate-icon1" alt="" src="/icon1.svg" />
                  <div className="zxreports2">Reports</div>
                  <div className="zxframe-item" />
                </div>
              </div>
            </Link>
          </div>
          <button onClick={handleSignOut} className="zxlogout3">
            <div className="zxlogout-container">
              <b className="zxlogout4">
                <p className="zxstudent">Logout</p>
              </b>
            </div>
          </button>
        </div>
        <div className="zxbody">
          <b className="zxreports3">Reports</b>
          <div className="zxbody1">
            <div className="zxsorry-for-the">Sorry for the inconvenience</div>
            <div className="zxplease-let-us-know-about-the-t-wrapper">
              <div className="zxplease-let-us">
                Please let us know about the trouble you are facing, so that we
                can assist you.
              </div>
            </div>
          </div>
          <img className="zximage-2-icon" alt="" src="/image-2@2x.png" />
          <Link className="zxbtn-report" to={"https://docs.google.com/forms/d/e/1FAIpQLSfP_FU4ybNTlmokrrryIzmeUzwU_8O7ConH7upMyXYZjKt4zQ/viewform"}>
            <img className="zxvector-icon" alt="" src="/vector.svg" />
          </Link>
        </div>
      </div>
      <div className="zxbot">
        <div className="zxreport2">
          <button className="zxreport-item" onClick={onReportClick}/>
          <div className="zxreport3">Report</div>
          <div className="zximage-1-container">
            <img className="zximage-1-icon1" alt="" src="/image-111@2x.png" />
          </div>
        </div>
        <img className="zxlogofpt-icon" alt="" src="/logolong-111@2x.png" />
      </div>
    </div>
  );
};

export default ReportSt;
