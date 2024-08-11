import React, {ReactNode} from 'react';
import './Main.css'
import Navigation from "../Navigation/Navigation";
import MainContent from "../MainContent/MainContent";
import ChangeForm from "../ChangeForm/ChangeForm";


const Main = ({page}:{page:ReactNode}) => {
    return (
        <div className='main__wrapper'>
            <Navigation/>
            <MainContent page={page} />
            <ChangeForm/>
        </div>
    );
};

export default Main;