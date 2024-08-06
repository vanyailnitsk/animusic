import React, {useContext} from 'react';
import './DataCore.css'
import avatar from "../../icons/avatar.jpeg";
import {Context} from "../../main.js";


const DataCore = ({page}) => {
    const {userStore} = useContext(Context)
    const logoutPic = () => {
        userStore.logout()
    }
    return (
        <div className='data__core__wrapper'>
            <div className='profile__picture__wrapper'>
                <img src={avatar} alt="" style={{width:'100%',display:'block'}} onClick={() => logoutPic()}/>
            </div>
            {page}
        </div>
    );
};

export default DataCore;