import React from 'react';
import Login from "../../components/Login/Login";
import styles from './Authorization.module.css'
import SignUp from "../../components/SignUp/SignUp";

const Authorization = () => {
    return (
        <div className={styles.authorization__wrapper}>
            <Login/>
            <SignUp/>
            <button onClick={() => localStorage.removeItem('token')}>Logout</button>
        </div>
    );
};

export default Authorization;