export {
    default as musicReducer,
    setPlaylist,
    changeVolume,
    setTrackIndex,
    setIsPlaying,
    togglePlayPause,
    nextTrack,
    previousTrack
} from './model/slice'
export {isTrackSaved,selectMusicState,selectCurrentTrack,isTrackEquals} from './model/selectors'
export type {MusicState} from './model/types'
export {addTrackToCollection,removeTrackFromCollection,fetchCollection} from './model/collectionThunks'
export {MusicPlayer} from './ui/music-player'
export {removeFromCollection,getCollection,addToCollection} from './api/collection'