import React from 'react';
import Soundtrack from "./Soundtrack";
import "../styles/SoundtrackList.css"
import {observer} from "mobx-react-lite";
import clock from '../images/clock.png'

const SoundtrackList = observer(({soundtracks}) => {
    return (
        <div className="soundtracklist__wrapper">
            <div className='header__soundracklist'>
                <span>#</span>
                <span className="anime__title__header">Anime title</span>
                <span className="original__title__header">Original title</span>
                <img src={clock} alt=""/>
            </div>
            {soundtracks.map((soundtrack,index) => (
                <Soundtrack
                    key={soundtrack.id}
                    soundtrackData={soundtrack}
                    playlist={soundtracks}
                    index={index}
                />
            ))}
        </div>
    );
});

export default SoundtrackList;