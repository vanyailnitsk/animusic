import React from 'react';
import './MainContent.css'
import menu from '../../icons/menu.png'
import DataCore from "../DataCore/DataCore";

const MainContent = ({page,setMenuActive,menuActive}) => {
    return (
        <div className={ menuActive? 'main__content__wrapper background__blur' : 'main__content__wrapper'}>
            <img src={menu} alt="" className={menuActive? 'hidden' : 'menu'} onClick={()=> setMenuActive(!menuActive)}/>
            <DataCore page={page}/>
        </div>
    );
};

export default MainContent;