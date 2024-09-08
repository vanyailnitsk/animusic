import styles from './current-track.module.css'
import {useNavigate} from "react-router-dom";
import {SaveTrack} from "@/features/collection";
import {useAppSelector} from "@/shared/lib/store";
import {selectCurrentTrack} from "@/entities/music";

export const CurrentTrack = () => {
    const currentMusicTrack = useAppSelector(selectCurrentTrack)
    const navigate = useNavigate()
    return (
        <div className={currentMusicTrack ? styles.current__track : styles.hidden}>
            <img
                src={currentMusicTrack && currentMusicTrack.image?.source || "images/track-img.jpeg"}
                alt=""
                className={styles.track__img}/>
            {currentMusicTrack &&
                <div className={styles.track__name}>
                            <span
                                onClick={() => navigate(`/album/${currentMusicTrack?.album.id}`)}
                                className={currentMusicTrack.originalTitle.length > 20 ? styles.scrolling : ""}>{currentMusicTrack.originalTitle}</span>
                    <span>{currentMusicTrack.animeTitle}</span>
                </div>
            }
            {currentMusicTrack && <SaveTrack className={styles.save__track} id={currentMusicTrack.id}/>}
        </div>
    );
}

