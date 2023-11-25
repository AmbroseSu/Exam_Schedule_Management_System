import React, { Children } from "react";
import { Navigate } from "react-router-dom";
import { UserAuth } from "../context/AuthContext";

const Proctected = ({ children }) => {
    const {user} = UserAuth();

    const emailDomain = user?.email?.split("@")[1]; // Extracting domain from email


    // if(!user){
    //     return <Navigate to ='/' />;
    // }else 
    if(emailDomain === "fpt.edu.vn"){
        return children;
    }else if(emailDomain === "gmail.com"){
        return children;
    }else if(localStorage.getItem("loginAdmin") === "Login Success"){
        return children;
    }else if (!user){
        return <Navigate to ='/' />;
    }




    // console.log(emailDomain);

    // if (emailDomain === "fpt.edu.vn") {
    //     // User with "@fpt.edu.vn" domain
    //     if (user.loggedIn) {
    //         // If logged in, redirect to "/proStu"
    //         return <Navigate to="/profile" />;
    //     }
    //     // If not logged in, allow access to the login page
    //     return children;
    // } else if (emailDomain === "fu.edu.vn") {
    //     // User with "@fu.edu.vn" domain
    //     if (user.loggedIn) {
    //         // If logged in, redirect to "/proLec"
    //         return <Navigate to="/proLec" />;
    //     }
    //     // If not logged in, allow access to the login page
    //     return children;
    // }else {
    //     return <Navigate to="/" />;
    // }


    return children;
    
};

export default Proctected;



