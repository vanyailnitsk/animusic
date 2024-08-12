import React, {createContext} from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import ContentStore from "./store/contentStore";
import EditBannerStore from "./store/EditBannerStore";
import EditAlbumStore from "./store/EditAlbumStore";
import EditSoundtrackStore from "./store/EditSoundtrackStore";
import UserStore from "./store/UserStore";

interface State{
    contentStore:ContentStore
    editBannerStore:EditBannerStore
    editAlbumStore:EditAlbumStore
    editSoundtrack:EditSoundtrackStore
    userStore:UserStore
}
const contentStore = new ContentStore()
const editBannerStore = new EditBannerStore()
const editAlbumStore = new EditAlbumStore()
const editSoundtrack = new EditSoundtrackStore()
const userStore = new UserStore()

export const Context = createContext<State>({contentStore, editBannerStore, editAlbumStore, editSoundtrack, userStore})
const root = ReactDOM.createRoot(document.getElementById('root') as HTMLElement);
root.render(
    <Context.Provider value={{contentStore, editBannerStore, editAlbumStore, editSoundtrack, userStore}}>
        <App/>
    </Context.Provider>
);

