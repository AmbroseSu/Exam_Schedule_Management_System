import { async } from "@firebase/util";
import React, { useState, useEffect, useCallback } from "react";
import { Link, Navigate, useNavigate } from "react-router-dom";
import { UserAuth } from "../../context/AuthContext";
import "./CreaExam.css";
import axios from "axios";


const CreaExam = () => {

  const navigate = useNavigate();
  const { logOut, user } = UserAuth();

  const handleSignOut = async () => {
    try {
      localStorage.removeItem("loginAdmin");
      navigate("/");
    } catch (error) {
      console.log(error);
    }
  }


 const navigator = useNavigate();


  const [examData, setExamData] = useState({
    startDate: '',
  });

  const [notification, setNotification] = useState('');
  const [notificationType, setNotificationType] = useState('success'); // 'success' or 'error'
  const [showNotification, setShowNotification] = useState(false);

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setExamData({ [name]: value });
  };

  function formatDate(inputDate) {
    const date = new Date(inputDate);
    
    const day = date.getDate();
    const month = date.getMonth() + 1; // Lưu ý rằng tháng trong JavaScript bắt đầu từ 0, nên cần cộng thêm 1
    const year = date.getFullYear();
  
    // Đảm bảo rằng ngày và tháng luôn có 2 chữ số
    const formattedDay = day < 10 ? `0${day}` : day;
    const formattedMonth = month < 10 ? `0${month}` : month;
  
    return `${formattedDay}/${formattedMonth}/${year}`;
  }

  const handleSubmit = async (event) => {
    event.preventDefault();
    console.log(formatDate(examData.startDate));
    const dataStart = formatDate(examData.startDate);
    console.log(dataStart);
    // try {
    //   const response = await axios.post("http://localhost:8888/generate", {
    //     dataStart
    //   });
    try {
      // const response = await fetch('http://localhost:8888/generate', {
      //   method: 'POST',
      //   headers: {
      //     'Content-Type': 'application/json',
      //   },
      //   body: JSON.stringify({ startDate: dataStart }),
      // });
      alert("Please wait show notification Create Success");
      fetch(`http://localhost:8888/generate?dateStart=${dataStart}`)
       .then(response => response.text())
        .then(data => {
          console.log(data);
          if(data == "Insert date false"){
            alert("Please insert date after today is 3 days.");
          }else{
            if(data == "Success"){
              alert("Create Exam Schedule Success.");
              navigator("/exscheduleExami");
            }
          }
          //alert("Create Successfully");
        })
        .catch(error => {
          console.log("Error:" + error);
        });
        
       

      if (!response.ok) {
        throw new Error('Network response was not ok');
      }
      const data = await response.json();
      //setNotification('Exam data submitted successfully');
      //setNotificationType('success');

      setExamData({
        startDate: '',
        endDate: ''
      });
    } catch (error) {
      //setNotification(error.message || 'An error occurred while submitting the data');
      //setNotificationType('error');
  
    } finally {

    }
  };

  const onReportClick = useCallback(() => {
    window.open('https://forms.gle/fCCNqjzx7UHx5X8Y6');
  }, []);

  return (
    <div className="creacreaexam">
    {showNotification && (
      <div className={`creanotification ${notificationType}`}>
        {notification}
      </div>
    )}
    <div className="creanavbar">
      <div className="creafpt">
        <div className="creafpt-child" />
        <div className="creafpt-item" />
        <div className="creaframe">
          <div className="creaexam-schedule">Exam schedule management system</div>
          <div className="creafpt-university">FPT UNIVERSITY</div>
        </div>
      </div>
      <button className="creaavatar">
        <img className="creaavt-icon" alt="" src="avt@2x.png"/>
        <div className="creastudent-name">
          <p className="crealecturer">Admin</p>
        </div>
      </button>
    </div>
    <div className="crearow">
      <div className="creamenu">
        <button className="creauser-profile">
          <img
            className="creaprofile-image-icon"
            alt=""
             src="avt@2x.png"
          />
          <div className="creaname-parent">
            <b className="creaname1">Admin</b>
            <div className="creatittle">admin@fpt.edu.vn</div>
          </div>
        </button>
        <div className="creamenu1">
          <Link className="creaexams" to={"/exscheduleExami"}>
            <div className="creaframe-parent">
              <div className="createmplate-parent">
                <img className="createmplate-icon" alt="" src="/template.svg" />
                <div className="creaexams1">Exams</div>
              </div>
            </div>
          </Link>
          <Link className="creacreateexams" to={"/createExamEx"}>
            <div className="creacreateexams-inner">
              <div className="createmplate-group">
                <img className="createmplate-icon1" alt="" src="/template1 copy.svg" />
                <div className="creacreate-exams">Create Exams</div>
                <div className="creaframe-item" />
              </div>
            </div>
          </Link>
          <Link className="creacreate-data" to={"/createDataEx"}>
            <div className="creaupward-trend-parent">
              <img
                className="creaupward-trend-icon"
                alt=""
                src="/upward-trend.svg"
              />
              <div className="creacreate-data1">Create Data</div>
            </div> 
          </Link>
          <Link className="creacreate-data" to={"/editExamina"}>
            <div className="creaupward-trend-parent">
              <img className="creauedit-icon" alt="" src="/uedit copy.svg" />
              <div className="creaedit1">Edit</div>
            </div>
          </Link>
          <Link className="creacreate-data" to={"/exportData"}>
            <div className="creaupward-trend-parent">
              <img className="creauedit-icon" alt="" src="/uexport.svg" />
              <div className="creaedit1">Export Data</div>
            </div>
          </Link>
          <Link className="creacreate-data" to={"/exReasonExami"}>
            <div className="creaupward-trend-parent">
              <img className="creauedit-icon" alt="" src="/icon31.svg" />
              <div className="creaedit1">Exam Reason</div>
            </div>
          </Link>
        </div>
        {/* <button onClick={handleSignOut} className="crealogout">
          <div className="crealogout-wrapper">
            <b className="crealogout1">
              <p className="crealecturer">Logout</p>
            </b>
          </div>
        </button> */}
        <button onClick={handleSignOut}  className="crealogout">
          <p className="crealecturer">
            Logout
          </p>
        </button>
      </div>
      <div className="creabody">
        <b className="creacreate-exam">Create Exam</b>
        <form onSubmit={handleSubmit} className="creaexam-form">
          <div className="creaform-group">
            <label htmlFor="startDate">Start Date:</label>
            <input 
              type="date" 
              id="startDate"
              name="startDate"
              value={examData.startDate}
              onChange={handleInputChange}
              className="creainput-style"
              required
            />
          </div>

          
          <button type="submit" className="creabtn-save">
            <img className="creauedit-icon" alt="" src="/usave.svg" />
            <b className="creasave">Save</b>
          </button>
        </form>
      </div>
    </div>
    <div className="creabot">
        <div className="creareport">
          <button className="creareport-child" onClick={onReportClick} />
          <div className="creareport1">Report</div>
          <div className="creaimage-1-wrapper">
            <img className="creaimage-1-icon" alt="" src="/image-1@2x.png" />
          </div>
        </div>
      <img className="crealogofpt-icon" alt="" src="/logofpt@2x.png" />
    </div>
  </div>
);
};

export default CreaExam;
