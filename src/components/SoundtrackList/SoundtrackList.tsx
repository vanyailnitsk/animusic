import React from 'react';
import Soundtrack from "../Soundtrack/Soundtrack";
import "./SoundtrackList.css"
import {observer} from "mobx-react-lite";
// import clock from '../icons/clock.png'
import {SoundtrackListProps} from '../../models/Soundtracks'
const SoundtrackList = observer(({playlist} :SoundtrackListProps) => {
    return (
        <div className="soundtracklist__wrapper">
            <div className='header__soundracklist'>
                <span>#</span>
                <span className="anime__title__header">Anime title</span>
                <span className="original__title__header">Original title</span>
                <div className='clock__icon'>
                    {/*<img src={clock} alt=""/>*/}
                </div>
            </div>
            {playlist.soundtracks.map((soundtrack,index) => (
                <Soundtrack
                    key={soundtrack.id}
                    soundtrackData={soundtrack}
                    playlist={playlist}
                    index={index}
                />
            ))}
        </div>
    );
});

export default SoundtrackList;