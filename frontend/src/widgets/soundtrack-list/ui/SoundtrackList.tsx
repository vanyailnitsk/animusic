import "./SoundtrackList.css"
import {observer} from "mobx-react-lite";
import {IPlaylistSoundtrack, ISoundtrack, PlaylistSoundtrack, Soundtrack} from "@/entities/soundtrack";
import {SoundtrackType} from "@/shared/types";
import clock from "@/shared/icons/clock.png";


interface SoundtrackListProps {
    soundtracks: ISoundtrack[] | IPlaylistSoundtrack[]
    type:SoundtrackType.album | SoundtrackType.playlist
}
export const SoundtrackList = observer(({soundtracks,type}: SoundtrackListProps) => {
    return (
        <div className="soundtrack__list__wrapper">
            {type === SoundtrackType.album? (
                <div className='header__soundtrack__list'>
                    <span>#</span>
                    <span className="anime__title__header">Anime title</span>
                    <span className="original__title__header">Original title</span>
                    <div className='clock__icon'>
                        <img src={clock} alt=""/>
                    </div>
                </div>
            ) : (
                <div className='header__soundtrack__list'>
                    <span>#</span>
                    <span className="playlist__anime__title__header">Anime title</span>
                    <span className="playlist__original__title__header">Original title</span>
                    <span className='added__at'>Added at</span>
                    <div className='playlist__clock__icon'>
                        <img src={clock} alt=""/>
                    </div>
                </div>
            )}
            {soundtracks.map((soundtrack,index) => (
                type === SoundtrackType.album?(
                    <Soundtrack
                        key={soundtrack.soundtrack.id}
                        soundtrackData={soundtrack.soundtrack}
                        listening_queue={soundtracks}
                        index={index}
                    />
                ):(
                    <PlaylistSoundtrack
                        key={soundtrack.soundtrack.id}
                        soundtrackData={soundtrack as IPlaylistSoundtrack}
                        listening_queue={soundtracks as IPlaylistSoundtrack[]}
                        index={index}
                    />
                )
            ))}
        </div>
    );
});

