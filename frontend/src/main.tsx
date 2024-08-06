import React, {createContext} from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import MusicStore from "./store/MusicStore";
import UserStore from "./store/UserStore";

interface State{
    userStore: UserStore,
    musicStore : MusicStore
}
const userStore = new UserStore()
const musicStore = new MusicStore()
const root = ReactDOM.createRoot(document.getElementById('root') as HTMLElement);
export const Context = createContext<State>({
    userStore,
    musicStore
});
root.render(
    <Context.Provider value={ {userStore, musicStore} }>
        <App/>
    </Context.Provider>
);

