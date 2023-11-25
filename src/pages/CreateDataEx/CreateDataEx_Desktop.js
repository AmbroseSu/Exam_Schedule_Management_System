import { async } from "@firebase/util";
import React, { useState, useEffect, useCallback } from "react";
import { Link, useNavigate } from "react-router-dom";
import { UserAuth } from "../../context/AuthContext";
import axios from 'axios';
import './CreateDataEx_Desktop.css';

const CreateDataEx_Desktop = () => {

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


  // States for loading
const [studentLoading, setStudentLoading] = useState(false);
const [lecturerLoading, setLecturerLoading] = useState(false);
const [courseLoading, setCourseLoading] = useState(false);
const [roomLoading, setRoomLoading] = useState(false);
  


  // States for Student
  const [studentFile, setStudentFile] = useState(null);
  const [studentFileName, setStudentFileName] = useState('');

  // States for Lecturer
  const [lecturerFile, setLecturerFile] = useState(null);
  const [lecturerFileName, setLecturerFileName] = useState('');

  const [courseFile, setCourseFile] = useState(null);
  const [courseFileName, setCourseFileName] = useState('');

  // States for Room
  const [roomFile, setRoomFile] = useState(null);
  const [roomFileName, setRoomFileName] = useState('');
  

  // Common function to handle file selection
  const handleFileChange = (setFile, setFileName) => (event) => {
    const file = event.target.files[0];
    const validExtensions = ['.xls', '.xlsx'];
    const fileExtension = file?.name.slice(((file?.name.lastIndexOf(".") - 1) >>> 0) + 2);

    if (file && validExtensions.includes('.' + fileExtension)) {
      setFile(file);
      setFileName(file.name);
    } else {
      alert('Please select an Excel file.');
      setFile(null);
      setFileName('');
    }
  };

  // Common function to handle file upload
  const handleUpload = (file, role) => async () => {
    if (!file) {
      alert(`Please select a file for ${role} first`);
      return;
    }

    const formData = new FormData();
    formData.append('file', file);
  // console.log(formData.get('file'));



  switch(role){
    case 'FullStudent':
      setStudentLoading(true);
      break;
    case 'Lecturer':
      setLecturerLoading(true);
      break;
    case 'Course':
      setCourseLoading(true);
      break;
    case 'Room':
      setRoomLoading(true);
      break;
    default:
      break;
  }


    // try {
      // Replace with your backend endpoint for the respective role
      // fetch(`http://localhost:8888/uploadFullStudent`, {
      //  method: 'POST',
      //  body: formData,
      //  })
      //  .then(response => response.json())
      // .then(data => {
      //  setLecturerData(data);
      //  })
      //  .catch(error => {
      //  console.error('Error calling API:', error);
      //   });
      // alert(`File for ${role} uploaded successfully: ` + response.data.message);
      
      try {
        if(role == "FullStudent"){
          alert("Please wait show notifications Upload Student Success");
        }
        const response = await fetch(`http://localhost:8888/upload${role}`, {
          method: 'POST',
          body: formData,
        });
    
        if (response.ok) {
          const data = await response.text();
          console.log(data); // Handle success response
          
          alert(data);
        } else {
          throw new Error('Network response was not ok');
        }
      } catch (error) {
        console.error('Error calling API:', error);
        alert('Error uploading file: ' + error.message);
      }finally{
        switch(role){
          case 'FullStudent':
            setStudentLoading(false);
            break;
          case 'Lecturer':
            setLecturerLoading(false);
            break;
          case 'Course':
            setCourseLoading(false);
            break;
          case 'Room':
            setRoomLoading(false);
            break;
          default:
            break;
        }
      }
    
  };

  // Common function to handle file download
  const handleDownload = (role) => async () => {
    try {
      // Replace with your backend endpoint for the respective role
      const response = await axios.get(`your-backend-endpoint-for-download/${role}`, {
        responseType: 'blob',
      });

      const file = new Blob([response.data], {
        type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet',
      });

      const fileURL = URL.createObjectURL(file);
      const fileLink = document.createElement('a');
      fileLink.href = fileURL;
      fileLink.setAttribute('download', `${role}.xlsx`);
      document.body.appendChild(fileLink);
      fileLink.click();
      fileLink.remove();
      URL.revokeObjectURL(fileURL);
    } catch (error) {
      alert(`Error downloading file for ${role}: ` + error.message);
    }
  };

  // Function to trigger the file input
  const triggerFileInput = (inputId) => () => {
    document.getElementById(inputId).click();
  };
  const onReportClick = useCallback(() => {
    window.open('https://forms.gle/fCCNqjzx7UHx5X8Y6');
  }, []);

  return (
    <div className="qcreatedata">
      <div className="qnavbar">
        <div className="qfpt">
          <div className="qfpt-child" />
          <div className="qfpt-item" />
          <div className="qframe">
            <div className="qexam-schedule">Exam schedule management system</div>
            <div className="qfpt-university">FPT UNIVERSITY</div>
          </div>
        </div>
        <button className="qavatar">
          <img className="qavt-icon" alt=""  src="avt@2x.png" />
          <div className="qstudent-name">
            <p className="qstudent">Admin</p>
          </div>
        </button>
      </div>
      <div className="qrow">
        <div className="qmenu">
          <button className="quser-profile">
            <img
              className="qprofile-image-icon"
              alt=""
              src="avt@2x.png"
            />
            <div className="qname-parent">
              <b className="qname1">Admin</b>
              <div className="qtittle">admin@fpt.edu.vn</div>
            </div>
          </button>
          <div className="qmenu1">
            <Link className="qexams" to={"/exscheduleExami"}>
              <div className="qframe-parent">
                <div className="qtemplate-parent">
                  <img className="qtemplate-icon" alt="" src="/template.svg" />
                  <div className="qexams1">Exams</div>
                </div>
              </div>
            </Link>
            <Link className="qcreate-exams" to={"/createExamEx"}>
              <div className="qupward-trend-parent">
                <img
                  className="qupward-trend-icon"
                  alt=""
                  src="/upward-trend.svg"
                />
                <div className="qcreate-exams1">Create Exams</div>
              </div>
            </Link>
            <Link className="qcreatedata1" to={"/createDataEx"}>
              <div className="qcreatedata-inner">
                <div className="qtemplate-group">
                  <img className="qtemplate-icon1" alt="" src="/template1 copy.svg" />
                  <div className="qcreate-data">Create Data</div>
                  <div className="qframe-item" />
                </div>
              </div>
            </Link>
            <Link className="qedit" to={"/editExamina"}>
              <div className="qupward-trend-parent">
                <img className="quedit-icon" alt="" src="/uedit.svg" />
                <div className="qcreate-exams1">Edit</div>
              </div>
            </Link>
            <Link className="qedit" to={"/exportData"}>
              <div className="qupward-trend-parent">
                <img className="quedit-icon" alt="" src="/uexport.svg" />
                <div className="qcreate-exams1">Export Data</div>
              </div>
            </Link>
            <Link className="qedit" to={"/exReasonExami"}>
              <div className="qupward-trend-parent">
                <img className="quedit-icon" alt="" src="/icon31.svg" />
                <div className="qcreate-exams1">Exam Reason</div>
              </div>
            </Link>
          </div>
          <button onClick={handleSignOut} className="qlogout">
            Logout
          </button>
        </div>
        <div className="qbody">
          <b className="qcreate-data1">Create Data</b>

          

          <div className="qlecturer">
            <div className="qstudent-wrapper">
              <div className="qstudent2">Course :</div>
            </div>
            <div className="qframe-group">
              <div className="qframe-wrapper">
                <input
                  type="file"
                  id="courseFileInput"
                  onChange={handleFileChange(setCourseFile, setCourseFileName)}
                  style={{ display: 'none' }}
                  accept=".xls,.xlsx"
                />
                <div className="qframe-div">
                  <div className="qframe-wrapper1" onClick={triggerFileInput('courseFileInput')}>
                    <button className="qbrowser-wrapper">
                      <div className="qbrowser">Browser</div>
                    </button>
                  </div>
                  <div className="qfile-name">{courseFileName}</div>
                </div>
              </div>
              <div className="quexport-parent" onClick={handleUpload(courseFile, 'Course')}>
              <button className="quexport-parent" >
                  <img className="quedit-icon" alt="" src="/uexport.svg" />
                  <div className="qbrowser">Upload</div>
                </button>
              </div>
              <div className="qfidownload-parent" >
                <Link className="qfidownload-parent" to={"https://drive.usercontent.google.com/download?id=1-DEYM_7HV_esRFq9bif-m7ilA3HtyNqe&export=download&authuser=0&confirm=t&uuid=d098c65d-1da4-4233-8771-196fbca122f6&at=APZUnTU_YBQgZfOO_Mjp_FzYmtsG:1700077169904"}>
                  <img className="quedit-icon" alt="" src="/fidownload.svg" />
                  <div className="qbrowser">Download</div>
                </Link>
              </div>
            </div>
          </div>

          <div className="qlecturer">
            <div className="qstudent-wrapper">
              <div className="qstudent2">Lecturer :</div>
            </div>
            <div className="qframe-group">
              <div className="qframe-wrapper">
                <input
                  type="file"
                  id="lecturerFileInput"
                  onChange={handleFileChange(setLecturerFile, setLecturerFileName)}
                  style={{ display: 'none' }}
                  accept=".xls,.xlsx"
                />
                <div className="qframe-div">
                  <div className="qframe-wrapper1" onClick={triggerFileInput('lecturerFileInput')}>
                    <button className="qbrowser-wrapper">
                      <div className="qbrowser">Browser</div>
                    </button>
                  </div>
                  <div className="qfile-name">{lecturerFileName}</div>
                </div>

                <div className="qfile-name">{lecturerFileName}</div>
              </div>
              <div className="quexport-parent" onClick={handleUpload(lecturerFile, 'Lecturer')}
              disabled={lecturerLoading}>
              <button className="quexport-parent" to={"http://localhost:8888/upLecturer"}>
                  <img className="quedit-icon" alt="" src="/uexport.svg" />
                  <div className="qbrowser">Upload</div>
                </button>
              </div>
              <div className="qfidownload-parent" >
                <Link className="qfidownload-parent" to={"https://drive.usercontent.google.com/download?id=1-AbvZhBlPtewbtg1ZPoZe4ctaN5pm7dV&export=download&authuser=0&confirm=t&uuid=58cfa337-c23d-4c1e-9dc4-488306cb927f&at=APZUnTXZfnSYZw8oGvGnpuaPM_eG:1700077233344"}>
                  <img className="quedit-icon" alt="" src="/fidownload.svg" />
                  <div className="qbrowser">Download</div>
                </Link>
              </div>
            </div>
          </div>

          <div className="qlecturer">
            <div className="qstudent-wrapper">
              <div className="qstudent2">Student :</div>
            </div>
            <div className="qframe-group">
              <div className="qframe-wrapper">
                <input
                  type="file"
                  id="studentFileInput" // Unique ID for student
                  onChange={handleFileChange(setStudentFile, setStudentFileName)}
                  style={{ display: 'none' }}
                  accept=".xls,.xlsx"
                />
                <div className="qframe-div">
                  <div className="qframe-wrapper1" onClick={triggerFileInput('studentFileInput')}>
                    <button className="qbrowser-wrapper">
                      <div className="qbrowser">Browser</div>
                    </button>
                    <div className="qfile-name">{studentFileName}</div>
                  </div>
                </div>
              </div>
              <div className="quexport-parent" 
              onClick={handleUpload(studentFile, 'FullStudent')}
              disabled={studentLoading}>
                <button className="quexport-parent" >
                  <img className="quedit-icon" alt="" src="/uexport.svg" />
                  <div className="qbrowser">Upload</div>
                </button>
              </div>
              <div className="qfidownload-parent" >
                <Link className="qfidownload-parent" to={"https://drive.usercontent.google.com/download?id=1-DaKpsTyoXWnFOvXSOVduqLMQPT6FKeM&export=download&authuser=0&confirm=t&uuid=78c64043-d32c-4ceb-9b9f-4a8a8ceb73d9&at=APZUnTWDwCSizxgfcEM91Zf7SJfN:1700077287743"}>
                  <img className="quedit-icon" alt="" src="/fidownload.svg" />
                  <div className="qbrowser">Download</div>
                </Link>
              </div>
            </div>
          </div>
          
          <div className="qlecturer">
            <div className="qstudent-wrapper">
              <div className="qstudent2">Room :</div>
            </div>
            <div className="qframe-group">
              <div className="qframe-wrapper">
                <input
                  type="file"
                  id="roomFileInput"
                  onChange={handleFileChange(setRoomFile, setRoomFileName)}
                  style={{ display: 'none' }}
                  accept=".xls,.xlsx"
                />
                <div className="qframe-div">
                  <div className="qframe-wrapper1" onClick={triggerFileInput('roomFileInput')}>
                    <button className="qbrowser-wrapper">
                      <div className="qbrowser">Browser</div>
                    </button>
                  </div>
                  <div className="qfile-name">{roomFileName}</div>
                </div>
              </div>
              <div className="quexport-parent" onClick={handleUpload(roomFile, 'Room')}>
              <button className="quexport-parent" to={"http://localhost:8888/upRoom"}>
                  <img className="quedit-icon" alt="" src="/uexport.svg" />
                  <div className="qbrowser">Upload</div>
                </button>
              </div>
              <div className="qfidownload-parent" >
                <Link className="qfidownload-parent" to={"https://drive.usercontent.google.com/download?id=1-EBjSHEjvqflkDhwmjYcjqmQsCUst2P0&export=download&authuser=0&confirm=t&uuid=4988ea8d-f1ce-467d-9257-e9dce682a6a4&at=APZUnTWPdDIjXPG1n57fSWzFSG77:1700077254555"}>
                  <img className="quedit-icon" alt="" src="/fidownload.svg" />
                  <div className="qbrowser">Download</div>
                </Link>
              </div>
            </div>
          </div>
        </div>
      </div>



      <div className="qbot">
      <div className="qreport">
          <button className="qreport-child" onClick={onReportClick}/>
          <div className="qreport1">Report</div>
          <div className="qimage-1-wrapper">
            <img className="qimage-1-icon" alt="" src="/image-1@2x.png" />
          </div>
        </div>
        <img className="qlogofpt-icon" alt="" src="/logofpt@2x.png" />
      </div>
    </div>
  );
};

export default CreateDataEx_Desktop;