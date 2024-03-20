import AppRouter from "./navigation/AppRouter";
import './styles/global.css'
import 'bootstrap/dist/css/bootstrap.css';
import {useContext, useEffect, useState} from "react";
import {Context} from "./index";
import {observer} from "mobx-react-lite";

function App() {
    const {userStore} = useContext(Context)
    useEffect(() => {
        if(localStorage.getItem('token')){
            userStore.checkAuth()
        }
    }, [userStore]);
    if(!userStore.isAuthInProgress){
        return (
            <div className='app'>
                <AppRouter/>
            </div>
        );
    }
}

export default observer(App);
