import {useEffect, useState} from 'react';
import {useLocation} from "react-router-dom";
import {getCollection, Playlist} from "@/entities/playlist";
import {COLLECTION} from "@/shared/consts";
import {SoundtrackList} from "@/widgets/soundtrack-list";


export const PlaylistPage = () => {
    const location = useLocation()
    const [playlist, setPlaylist] = useState<Playlist | null>(null)
    useEffect(() => {
        if (location.pathname === COLLECTION){
            getCollection()
                .then(response => {
                    setPlaylist(response.data)
                    return response.data.soundtracks
                })
                .catch(error => {
                console.log(error)
            })
        }
    }, []);
    return (
        <div>
            {playlist &&
                <div>
                    <SoundtrackList soundtracks={playlist.soundtracks} />
                </div>

            }
        </div>
    );
};

