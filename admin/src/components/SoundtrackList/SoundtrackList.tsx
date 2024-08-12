import React, {useContext} from 'react';
import Soundtrack from "../Soundtrack/Soundtrack";
import "./SoundtrackList.css"
import clock from '../../icons/clock.png'
import {SoundtrackListProps} from '../../models/Soundtracks'
import {Context} from "../../index";
import {ADD_SOUNDTRACK} from "../../Forms/vatietyForms";
const SoundtrackList = ({soundtracks} :SoundtrackListProps) => {
    const {contentStore} = useContext(Context)
    return (
        <div className="soundtracklist__wrapper">
            <div className='header__soundracklist'>
                <span>#</span>
                <span className="anime__title__header">Anime title</span>
                <span className="original__title__header">Original title</span>
                <div className='clock__icon'>
                    <img src={clock} alt=""/>
                </div>
            </div>
            <div style={{display:'flex',justifyContent:"center"}}>
                <span style={{fontSize:30,cursor:'pointer'}} onClick={() => contentStore.setCurrentContentType(ADD_SOUNDTRACK)}>+</span>
            </div>
            {soundtracks.map((soundtrack,index) => (
                <Soundtrack
                    key={soundtrack.soundtrack.id}
                    soundtrackData={soundtrack.soundtrack}
                    index={index}
                />
            ))}
        </div>
    );
};

export default SoundtrackList;