import {useContext, useEffect, useState} from 'react';
import {useLocation} from "react-router-dom";
import {getCollection, Playlist} from "@/entities/playlist";
import {COLLECTION} from "@/shared/consts";
import {SoundtrackList} from "@/widgets/soundtrack-list";
import {Context} from "@/main.tsx";
import {MusicService} from "@/shared/services";
import {Simulate} from "react-dom/test-utils";
import play = Simulate.play;
import {observer} from "mobx-react-lite";


export const PlaylistPage = observer(() => {
    const location = useLocation()
    const {musicStore} = useContext(Context)
    const [playlist, setPlaylist] = useState<Playlist | null>(null)
    useEffect(() => {
        if (location.pathname === COLLECTION){
            MusicService.getCollection()
                .then(response => {
                    setPlaylist(response)
                })
                .catch(error => {
                console.log(error)
            })
        }
    }, [musicStore.fav_tracks]);
    return (
        <div>
            {playlist &&
                <div>
                    <SoundtrackList soundtracks={playlist.soundtracks} />
                </div>

            }
        </div>
    );
});

