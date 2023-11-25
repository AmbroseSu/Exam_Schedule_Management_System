import { useCallback } from "react";
import React, { useEffect } from "react";
import { UserAuth } from "../../context/AuthContext";
import { useNavigate } from "react-router-dom";
import "./Mobile_login.css";
import { async } from "@firebase/util";
import GoogleButton from "react-google-button";
import axios from "axios";

const Mobile_login = () => {

  const { googleSignIn, user} = UserAuth();
  const navigate = useNavigate();

  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  // async function login(event) {
  //   event.preventDefault();
  //   try{
  //     await axios.post("http://localhost:8888/login/admin", {
  //       username: username,
  //       password: password,
  //     }).then((res) => {
  //       console.log(res.data);
        
  //       if(res.data.message == "Username not exits")
  //       {
  //         alert("Email not exists");
  //       }
  //       else if(res.data.message == "Login Success")
  //       {
  //         navigate("/examschex");
  //       }
  //       else
  //       {
  //         alert("Incorrect Email and Password not match");
  //       }
  //     }, fail => {
  //       console.log(fail);
  //     });
  //   }
  //   catch (err) {
  //     alert (err);
  //   }
  // }

  async function login(event) {
    event.preventDefault();
    try {
      const res = await axios.post("http://localhost:8888/login/admin", {
        username: username,
        password: password,
      });

      console.log(res.data);

      if (res.data.message === "Username not exists") {
        alert("Email not exists");
      } else if (res.data.message === "Login Success") {
        navigate("/examschex");
      } else {
        alert("Incorrect Email and Password not match");
      }
    } catch (err) {
      console.error(err);
      alert(err);
    }
  }


  const handleGoogleSingIn = async () => {
    try{
      await googleSignIn();
    }catch(error){
      console.log(error);
    }
  };

  useEffect(() => {
    if(user != null){
      navigate('/profileSt');
    }
  },[user]);

  const onMainButtonClick = useCallback(() => {
    window.open("localhost:8888/loginStudents");
  }, []);

  const onSocialButtonContainerClick = useCallback(() => {
    window.location.href = "localhost:8888/loginStudents";
  }, []);

  return (
    <div className="mobile">
      <div className="mobile1">
        <div className="login-art-wrapper">
          <img className="login-art1" alt="" src="/login-art1@2x.png" />
        </div>
        <div className="login-form1">
          <div className="intro1">
            <div className="welcome-fpt-saigon">Welcome FPT Saigon 👋</div>
            <div className="please-enter-your">
              Please enter your login details to sign in
            </div>
          </div>
          <div className="form1">
            <div className="input6">
              <div className="label4">Username</div>
              <input
                className="username1"
                placeholder="Example@email.com"
                type="text"
              />
            </div>
            <div className="input6">
              <div className="label4">Password</div>
              <input
                className="username1"
                placeholder="At least 8 characters"
                type="password"
              />
            </div>
            <a
              className="forgot-password1"
              href={`https://www.canva.com/design/DAFwFwf8Ksg/IaVVcwGakZNm2lFrMgOfQA/view?utm_content=DAFwFwf8Ksg&utm_campaign=designshare&utm_medium=link&utm_source=publishsharelink`}
              target="_blank"
            >
              Forgot Password?
            </a>
            <button className="main-button1" onClick={login}>
              <div className="sign-in3">Log in</div>
            </button>
          </div>
          <div className="spcial-login">
            <div className="or1">
              <div className="or-inner" />
              <div className="label4">Or sign in with</div>
              <div className="or-inner" />
            </div>
            <div className="social-buttons-columns1">
              {/* <div
                className="social-button"
                onClick={onSocialButtonContainerClick}
              >
                <img className="google-icon3" alt="" src="/google3.svg" />
                <img className="facebook-icon3" alt="" src="/facebook3.svg" />
                <div className="facebook1">Google</div>
              </div> */}
              <GoogleButton onClick={handleGoogleSingIn}/>
            </div>
          </div>
        </div>
        <img className="logo-long-2-icon1" alt="" src="/logolong-21@2x.png" />
      </div>
    </div>
  );
};

export default Mobile_login;
