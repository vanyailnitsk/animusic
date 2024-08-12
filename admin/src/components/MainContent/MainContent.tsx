import React, {ReactNode, useContext} from 'react';
import './MainContent.css'
import DataCore from "../DataCore/DataCore";
import {observer} from "mobx-react";


const MainContent = observer(({page} : {page:ReactNode}) => {
    return (
        <div className='main__content__wrapper'>
            <DataCore page={page}/>
        </div>
    );
});

export default MainContent;