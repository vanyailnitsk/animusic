import {createContext} from 'react';
import ReactDOM from 'react-dom/client';
import App from "@/app/App";
import {MusicStore, UserStore} from "@/shared/store";
import {Provider} from "react-redux";
import store from "@/app/store.ts";

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
        <Provider store={store}>
            <App/>
        </Provider>
    </Context.Provider>
);

