import React from 'react';
import './Navigation.css'
import homeIcon from '../../icons/home.png'
import {useNavigate} from "react-router-dom";
import {HOME_ROUTE} from "../../navigation/routes";
const Navigation = () => {
    const navigate = useNavigate();
    const handleRoute = (route:string) =>{
        navigate(route)
    }
    return (
        <div className='navigation__wrapper'>

            <div className="nav__block">
                <div className='nav_title' onClick={() =>handleRoute(HOME_ROUTE)}>
                    <img style={{width:26}} src={homeIcon} alt=""/>
                    <span>ANIME</span>
                </div>
            </div>
        </div>
    );
};

export default Navigation;