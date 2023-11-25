import { async } from "@firebase/util";
import React, { useState, useEffect, useCallback } from "react";
import { Link, useNavigate } from "react-router-dom";
import { UserAuth } from "../../context/AuthContext";
import "./Exschedulestu.css";

const Exschedulestu = () => {


    const { logOut, user } = UserAuth();

    const handleSignOut = async () => {
        try {
            await logOut();
        } catch (error) {
            console.log(error);
        }
    }


    const [studentData, setStudentData] = useState([]);
    // const [lecturerData, setLecturerData] = useState(null);
    const [loading, setLoading] = useState(true);
    const navigate = useNavigate();
    console.log(localStorage.getItem("email"));
  const disable = localStorage.getItem("email");
    useEffect(() => {
        fetch(`http://localhost:8888/viewexamstudent?email=${disable}`)
            .then(response => response.json())
            .then(data => {
                setStudentData(data);
                setLoading(false);
            })
            .catch(error => {
                console.error('Error to call API:', error);
                setLoading(false);
            });
    }, []);
    // const onProStuClick = useCallback(() => {
    //     navigate("/profileSt");
    //   }, []);

    const onReportClick = useCallback(() => {
        window.open('https://forms.gle/fCCNqjzx7UHx5X8Y6');
    }, []);

    return (
        <div className="fexschedulelec">
            <div className="fnavbar1">
                <div className="ffpt1">
                    <div className="ffpt-inner" />
                    <div className="frectangle-div" />
                    <div className="fframe">
                        <div className="fexam-schedule1">
                            Exam schedule management system
                        </div>
                        <div className="ffpt-university1">FPT UNIVERSITY</div>
                    </div>
                </div>
                <button className="favatar1">
                    <img className="favt-icon" alt="" src={user?.photoURL} referrerPolicy="no-referrer" />
                    <div className="fstudent-name">
                        <p className="fstudent">{user?.displayName}</p>
                    </div>
                </button>
            </div>
            <div className="frow">
                <div className="fmenu">
                    <button className="fuser-profile1">
                        <img
                            className="fprofile-image-icon1"
                            alt=""
                            src={user?.photoURL} referrerPolicy="no-referrer"
                        />
                        <div className="fname-group">
                            <b className="fname1">{user?.displayName}</b>
                            <div className="ftittle1">{user?.email}</div>
                        </div>
                    </button>
                    <div className="fexam-parent">
                        <Link className="fregister" to={"/examscheduleStu"}>
                            <div className="fregister-inner">
                                <div className="ficon-container">
                                    <img className="ficon2" alt="" src="/icon2.svg" />
                                    <div className="fexams2">Exams</div>
                                    <div className="fframe-item" />
                                </div>
                            </div>
                        </Link>
                        {/* <button className="profile3" onClick={onProStuClick}>
                            <div className="homeoutline-parent">
                                <img
                                    className="homeoutline-icon"
                                    alt=""
                                    src="/hxomeoutline.svg"
                                />
                                <div className="profile4">Profile</div>
                            </div>
                        </button> */}

                        <Link className="fprofile3" to={"/profileSt"}>
                            <div className="fhomeoutline-parent">
                                <img
                                    className="fhomeoutline-icon"
                                    alt=""
                                    src="/homeoutline.svg"
                                />
                                <div className="fprofile4">Profile</div>
                            </div>
                        </Link>

                        <Link className="fprofile3" to={"/reportSt"}>
                            <div className="fhomeoutline-parent">
                                <img
                                    className="fupward-trend-icon"
                                    alt=""
                                    src="/icon1.svg"
                                />
                                <div className="fprofile4">Reports</div>
                            </div>
                        </Link>
                    </div>
                    {/* <button className="logout3">
                        <div className="logout-container">
                            <b className="logout4">
                                <p className="logout5">Logout</p>
                            </b>
                        </div>
                    </button> */}
                    <button onClick={handleSignOut} className="flogout3">
                        <p className="flogout5">
                        Logout
                        </p>
                    </button>
                </div>
                <div className="fbody">
                    <b className="fexam-schedule2">Exam Schedule</b>
                    <div className="fcourses-parent">
                        <div className="fcourses">Courses</div>
                        <div className="froom">Room</div>
                        <div className="fdate">Date</div>
                        <div className="fslot">Slot</div>
                        {/* <div className="fstatus">Status</div> */}
                    </div>
                    {/* <div className="rectangle-parent">
                        <div className="frame-inner" />
                        <div className="csd201-parent">
                            <div className="csd201">CSD201</div>
                            <div className="div">302</div>
                            <div className="div1">20/07/2023</div>
                            <div className="div2">7:30 - 9:00</div>
                            <div className="done">Done</div>
                        </div>
                    </div>
                    <div className="rectangle-parent">
                        <div className="frame-inner" />
                        <div className="csd201-parent">
                            <div className="csd201">CSD201</div>
                            <div className="div">302</div>
                            <div className="div1">20/07/2023</div>
                            <div className="div2">7:30 - 9:00</div>
                            <div className="not-yet">Not Yet</div>
                        </div>
                    </div>
                    <div className="rectangle-parent">
                        <div className="frame-inner" />
                        <div className="csd201-parent">
                            <div className="csd201">CSD201</div>
                            <div className="div">302</div>
                            <div className="div1">20/07/2023</div>
                            <div className="div2">7:30 - 9:00</div>
                            <div className="done">Done</div>
                        </div>
                    </div>
                    <div className="rectangle-parent">
                        <div className="frame-inner" />
                        <div className="csd201-parent">
                            <div className="csd201">CSD201</div>
                            <div className="div">302</div>
                            <div className="div1">20/07/2023</div>
                            <div className="div2">7:30 - 9:00</div>
                            <div className="done">Done</div>
                        </div>
                    </div>
                    <div className="rectangle-parent">
                        <div className="frame-inner" />
                        <div className="csd201-parent">
                            <div className="csd201">CSD201</div>
                            <div className="div">302</div>
                            <div className="div1">20/07/2023</div>
                            <div className="div2">7:30 - 9:00</div>
                            <div className="done">Done</div>
                        </div>
                    </div>
                    <div className="rectangle-parent">
                        <div className="frame-inner" />
                        <div className="csd201-parent">
                            <div className="csd201">CSD201</div>
                            <div className="div">302</div>
                            <div className="div1">20/07/2023</div>
                            <div className="div2">7:30 - 9:00</div>
                            <div className="done">Done</div>
                        </div>
                    </div>
                    <div className="rectangle-parent">
                        <div className="frame-inner" />
                        <div className="csd201-parent">
                            <div className="csd201">CSD201</div>
                            <div className="div">302</div>
                            <div className="div1">20/07/2023</div>
                            <div className="div2">7:30 - 9:00</div>
                            <div className="done">Done</div>
                        </div> */}
                        <div className="bodyexScheStu">
            {/* Render danh sách khuôn mẫu */}
            {studentData && studentData.length > 0 ? (
              studentData.map((studentData, index) => (
                <div className="frectangle-parent" key={index}>
                  <div className="fframe-inner" />
                  <div className="fcsd201-parent">
                    <div className="fcsd201">{loading ? 'Loading...' : studentData.course_id}</div>
                    <div className="fdiv">{loading ? 'Loading...' : studentData.Room_id}</div>
                    <div className="fdiv1">{loading ? 'Loading...' : studentData.Date}</div>
                    <div className="fdiv2">{loading ? 'Loading...' : studentData.Time}</div>
                    <div className="done"></div>
                  </div>
                  </div>
                  )))
                  : (
                  <div>No data available</div>
              )}
                    </div>
                </div>
            </div>
            <div className="fbot">
                <div className="freport2">
                    <button className="freport-item" onClick={onReportClick}/>
                    <div className="freport3">Report</div>
                    <div className="fimage-1-container">
                        <img className="fimage-1-icon1" alt="" src="/image-1@2x.png" />
                    </div>
                </div>
                <img className="flogofpt-icon" alt="" src="/logolong-1@2x.png" />
            </div>
        </div>
    );
};

export default Exschedulestu;