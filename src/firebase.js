// Import the functions you need from the SDKs you need
import { initializeApp } from "firebase/app";
import { getAuth } from "firebase/auth";
// import { getAuth, GoogleAuthProvider, signInWithPopup } from "firebase/auth";


// TODO: Add SDKs for Firebase products that you want to use
// https://firebase.google.com/docs/web/setup#available-libraries

// Your web app's Firebase configuration
const firebaseConfig = {
  apiKey: "AIzaSyAll5-8ICAaD8OBpuw1VNhgaK71E7xZUEo",
  authDomain: "auth-login-ee505.firebaseapp.com",
  projectId: "auth-login-ee505",
  storageBucket: "auth-login-ee505.appspot.com",
  messagingSenderId: "349116833787",
  appId: "1:349116833787:web:f6516bf73e8425d2cb755e"
};

// Initialize Firebase
const app = initializeApp(firebaseConfig);
export const auth = getAuth(app);

// const provider = new GoogleAuthProvider();

// export const handleGoogleSignIn = () => {
//   signInWithPopup(auth, provider)
//   .then((result) => {
//     const name = result.user.displayName;
//     const email = result.user.email;
//     const profilePic = result.user.profilePic;

//     localStorage.setItem("name", name);
//     localStorage.setItem("email", email);
//     localStorage.setItem("profilePic", profilePic);
//   })
//   .catch((error) => {
//     console.log(error);
//   });
// };