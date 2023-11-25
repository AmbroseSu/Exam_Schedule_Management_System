import { useContext, createContext, useEffect, useState } from "react";
import {
    GoogleAuthProvider,
    signInWithPopup,
    signInWithRedirect,
    signOut,
    onAuthStateChanged
} from "firebase/auth";
import { auth } from "../firebase";


const AuthContext = createContext()

export const AuthContextProvider = ({ children }) => {

    const [user, setUser] = useState({});

    const googleSignIn = () => {
        const provider = new GoogleAuthProvider();
        // signInWithPopup(auth, provider);
        signInWithRedirect(auth, provider);
    };

    const logOut = () => {
        signOut(auth)
        localStorage.removeItem("email");
    }

    useEffect(() => {
        const unsubscribe = onAuthStateChanged(auth, (currentUser) => {
            setUser(currentUser)
            console.log('User', currentUser)
            // localStorage.setItem("name", currentUser.displayName);
            // localStorage.setItem("email", currentUser.email);
            if(currentUser){
                localStorage.setItem("email", currentUser.email);
            }
        });
        return () => {
            unsubscribe();
        }
    }, [])

    return (
        <AuthContext.Provider value={{ googleSignIn, logOut, user }}>
            {children}
        </AuthContext.Provider>
    )
}

export const UserAuth = () => {
    return useContext(AuthContext)
}