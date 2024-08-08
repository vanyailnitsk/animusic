import "./SoundtrackList.css"
import {observer} from "mobx-react-lite";
import clock from '@/shared/icons/clock.png'
import {ISoundtrack, PlaylistSoundtrack, Soundtrack} from "@/entities/soundtrack";

interface SoundtrackListProps {
    soundtracks: ISoundtrack[] | PlaylistSoundtrack[]
}

export const SoundtrackList = observer(({soundtracks}: SoundtrackListProps) => {
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
            {soundtracks.map((soundtrack,index) => (
                <Soundtrack
                    key={soundtrack.soundtrack.id}
                    soundtrackData={soundtrack.soundtrack}
                    listening_queue={soundtracks}
                    index={index}
                />
            ))}
        </div>
    );
});

