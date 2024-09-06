import {createContext} from 'react';
import ReactDOM from 'react-dom/client';
import App from "@/app/App";
import {UserStore} from "@/shared/store";
import {Provider} from "react-redux";
import store from "@/app/store.ts";

interface State{
    userStore: UserStore,
}
const userStore = new UserStore()
const root = ReactDOM.createRoot(document.getElementById('root') as HTMLElement);
export const Context = createContext<State>({
    userStore,
});
root.render(
    <Context.Provider value={ {userStore} }>
        <Provider store={store}>
            <App/>
        </Provider>
    </Context.Provider>
);

