import { async } from "@firebase/util";
import React, { useState, useEffect, useCallback } from "react";
import { Link } from "react-router-dom";
import { UserAuth } from "../../context/AuthContext";
import "./ExscheduleLec.css";

const ExscheduleLec = () => {

  const { logOut, user } = UserAuth();

  const handleSignOut = async () => {
    try {
      await logOut();
    } catch (error) {
      console.log(error);
    }
  }

  const handleDeleteClick = async (index, deleteReasons) => {
    try {
      // Giả sử cấu trúc dữ liệu có Slot_id và lecturer_Email
      const selectedLecturer = lecturerData[index];

      console.log(localStorage.getItem("email"));
      console.log(selectedLecturer.slot_id);
      console.log(deleteReasons);
      fetch(`http://localhost:8888/deleteLecture?emailLecture=${localStorage.getItem("email")}&slotId=${selectedLecturer.slot_id}&reason=${deleteReasons}`)
        .then(response => response.text())
        .then(data => {
          //setStudentData(data);
          //alert(data);
          if (data == "Success") {
            const updatedData = [...lecturerData];
            updatedData.splice(index, 1);
            setLecturerData(updatedData);
            alert("Delete Successfully");
          }else{
            alert(" Too late to delete")
          }
        })
        .catch(error => {
          console.error('Error deleting lecturer:', error);
        });
      // Xóa phần tử tại vị trí index khỏi mảng lecturerData
      // if (data == "Success") {
      //   const updatedData = [...lecturerData];
      //   updatedData.splice(index, 1);
      //   setLecturerData(updatedData);
      // }

      // const deleteReason = [...deleteReasons];
      // deleteReason.splice(index);
      // setDeleteReasons(deleteReason);
      resetDeleteReason(index);
    } catch (error) {
      console.error('Đã xảy ra lỗi:', error);
    }

  };


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

  const [deleteReasons, setDeleteReasons] = useState(Array.from({ length: lecturerData.length }, () => ''));

  const handleReasonsChange = (index, value) => {
    const newReasons = [...deleteReasons];
    newReasons[index] = value;
    setDeleteReasons(newReasons);
  }

  const resetDeleteReason = (index) => {
    const newReasons = [...deleteReasons];
    newReasons[index] = '';
    setDeleteReasons(newReasons);
  }

  const onReportClick = useCallback(() => {
    window.open('https://forms.gle/fCCNqjzx7UHx5X8Y6');
  })
  return (
    <div className="cexschedulelec">
      <div className="cnavbar1">
        <div className="cfpt1">
          <div className="cfpt-inner" />
          <div className="crectangle-div" />
          <div className="cframe">
            <div className="cexam-schedule1">
              Exam schedule management system
            </div>
            <div className="cfpt-university1">FPT UNIVERSITY</div>
          </div>
        </div>
        <button className="cavatar1">
          <img className="cavt-icon" alt="" src={user?.photoURL} referrerPolicy="no-referrer" />
          <div className="cstudent-name">
            <p className="cstudent">{user?.displayName}</p>
          </div>
        </button>
      </div>
      <div className="crow">
        <div className="cmenu">
          <button className="cuser-profile1">
            <img
              className="cprofile-image-icon1"
              alt=""
              src={user?.photoURL} referrerPolicy="no-referrer"
            />
            <div className="cname-group">
              <b className="cname2">{user?.displayName}</b>
              <div className="ctittle1">{user?.email}</div>
            </div>
          </button>
          <div className="cregister-parent">
            <Link className="cregister" to={"/examscheduleLec"}>
              <div className="cregister-inner">
                <div className="cicon-container">
                  <img className="cicon2" alt="" src="/icon2.svg" />
                  <div className="cexams2">Exams</div>
                  <div className="cframe-item" />
                </div>
              </div>
            </Link>
            <Link className="cprofile3" to={"/profileLe"}>
              <div className="chomeoutline-parent">
                <img
                  className="chomeoutline-icon"
                  alt=""
                  src="/homeoutline.svg"
                />
                <div className="cprofile4">Profile</div>
              </div>
            </Link>
            <Link className="cprofile3" to={"/registerLec"}>
              <div className="chomeoutline-parent">
                <img
                  className="cupward-trend-icon"
                  alt=""
                  src="/upward-trend.svg"
                />
                <div className="cprofile4">Register</div>
              </div>
            </Link>
            <Link className="csalary" to={"/salary"}>
              <div className="cregister-inner">
                <div className="cfidollar-sign-parent">
                  <img
                    className="chomeoutline-icon"
                    alt=""
                    src="/fidollarsign.svg"
                  />
                  <div className="cprofile4">Salary</div>
                </div>
              </div>
            </Link>
            <Link className="csalary" to={"/reportLec"}>
              <div className="cregister-inner">
                <div className="cfidollar-sign-parent">
                  <img className="chomeoutline-icon" alt="" src="/icon3.svg" />
                  <div className="cprofile4">Reports</div>
                </div>
              </div>
            </Link>
          </div>
          {/* <button className="logout3">
            <div className="logout-container">
              <b className="logout4">
                <p className="student">Logout</p>
              </b>
            </div>
          </button> */}
          <button onClick={handleSignOut} className="clogout3">
            <p className="cstudent">Logout</p>
          </button>
        </div>
        <div className="cbody">
          <b className="cexam-schedule2">Exam Schedule</b>
          <div className="ccourses-parent">
            <div className="ccourses">Courses</div>
            <div className="croom">Room</div>
            <div className="cdate">Date</div>
            <div className="cslot">Slot</div>
          </div>
          {/* <div className="rectangle-parent">
            <div className="frame-inner" />
            <div className="csd201-parent">
              <div className="csd201">CSD201</div>
              <div className="div">302</div>
              <div className="div1">20/07/2023</div>
              <div className="div2">7:30 - 9:00</div>
              <button className="vector-wrapper">
                <img className="vector-icon" alt="" src="/vector.svg" />
              </button>
              <button className="vector-container">
                <img className="vector-icon1" alt="" src="/vector1.svg" />
              </button>
            </div>
          </div>
          <div className="rectangle-parent">
            <div className="frame-inner" />
            <div className="csd201-parent">
              <div className="csd201">CSD201</div>
              <div className="div">302</div>
              <div className="div1">20/07/2023</div>
              <div className="div2">7:30 - 9:00</div>
              <button className="vector-wrapper">
                <img className="vector-icon" alt="" src="/vector.svg" />
              </button>
              <button className="vector-container">
                <img className="vector-icon1" alt="" src="/vector1.svg" />
              </button>
            </div>
          </div>
          <div className="rectangle-parent">
            <div className="frame-inner" />
            <div className="csd201-parent">
              <div className="csd201">CSD201</div>
              <div className="div">302</div>
              <div className="div1">20/07/2023</div>
              <div className="div2">7:30 - 9:00</div>
              <button className="vector-wrapper">
                <img className="vector-icon" alt="" src="/vector.svg" />
              </button>
              <button className="vector-container">
                <img className="vector-icon1" alt="" src="/vector1.svg" />
              </button>
            </div>
          </div>
          <div className="rectangle-parent">
            <div className="frame-inner" />
            <div className="csd201-parent">
              <div className="csd201">CSD201</div>
              <div className="div">302</div>
              <div className="div1">20/07/2023</div>
              <div className="div2">7:30 - 9:00</div>
              <button className="vector-wrapper">
                <img className="vector-icon" alt="" src="/vector.svg" />
              </button>
              <button className="vector-container">
                <img className="vector-icon1" alt="" src="/vector1.svg" />
              </button>
            </div>
          </div>
          <div className="rectangle-parent">
            <div className="frame-inner" />
            <div className="csd201-parent">
              <div className="csd201">CSD201</div>
              <div className="div">302</div>
              <div className="div1">20/07/2023</div>
              <div className="div2">7:30 - 9:00</div>
              <button className="vector-wrapper">
                <img className="vector-icon" alt="" src="/vector.svg" />
              </button>
              <button className="vector-container">
                <img className="vector-icon1" alt="" src="/vector1.svg" />
              </button>
            </div>
          </div>
          <div className="rectangle-parent">
            <div className="frame-inner" />
            <div className="csd201-parent">
              <div className="csd201">CSD201</div>
              <div className="div">302</div>
              <div className="div1">20/07/2023</div>
              <div className="div2">7:30 - 9:00</div>
              <button className="vector-wrapper">
                <img className="vector-icon" alt="" src="/vector.svg" />
              </button>
              <button className="vector-container">
                <img className="vector-icon1" alt="" src="/vector1.svg" />
              </button>
            </div>
          </div>
          <div className="rectangle-parent">
            <div className="frame-inner" />
            <div className="csd201-parent">
              <div className="csd201">CSD201</div>
              <div className="div">302</div>
              <div className="div1">20/07/2023</div>
              <div className="div2">7:30 - 9:00</div>
              <button className="vector-wrapper">
                <img className="vector-icon" alt="" src="/vector.svg" />
              </button>
              <button className="vector-container">
                <img className="vector-icon1" alt="" src="/vector1.svg" />
              </button>
            </div>
          </div> */}

          <div className="bodyexScheLec">
            {/* Render danh sách khuôn mẫu */}
            {lecturerData && lecturerData.length > 0 ? (
              lecturerData.map((lecturerData, index) => (
                <div className="crectangle-parent" key={index}>
                  <div className="cframe-inner" />
                  <div className="ccsd201-parent">
                    <div className="ccsd201">{loading ? 'Loading...' : lecturerData.course_id}</div>
                    <div className="cdiv">{loading ? 'Loading...' : lecturerData.Room_id}</div>
                    <div className="cdiv1">{loading ? 'Loading...' : lecturerData.Date}</div>
                    <div className="cdiv2">{loading ? 'Loading...' : lecturerData.Time}</div>
                    {/* <button className="cvector-wrapper">
                      <img className="cvector-icon" alt="" src="/vector.svg" />
                    </button> */}
                    <input type="text" placeholder="Delete reason" value={deleteReasons[index]}
                      onChange={(e) => handleReasonsChange(index, e.target.value)}
                    />
                    <button className="cvector-container" onClick={() => handleDeleteClick(index, deleteReasons[index])}>
                      <img className="cvector-icon1" alt="" src="/vector1.svg" />
                    </button>
                  </div>
                </div>
              )))
              : (
                <div>No data available</div>
              )}
          </div>

        </div>
      </div>
      <div className="cbot">
        <div className="creport2">
          <button className="creport-item" onClick={onReportClick} />
          <div className="creport3">Report</div>
          <div className="cimage-1-container">
            <img className="cimage-1-icon1" alt="" src="/image-1111@2x.png" />
          </div>
        </div>
        <img className="clogofpt-icon" alt="" src="/logofpt1@2x.png" />
      </div>
    </div>
  );
};

export default ExscheduleLec;