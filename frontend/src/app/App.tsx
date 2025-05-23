import './styles/global.css'
import {useContext, useEffect, useState} from "react";
import {observer} from "mobx-react-lite";
import {AppRouter} from "@/app/routers";
import {Context} from "@/main.tsx";
import {SnackbarProvider} from "notistack";

function App() {
    const {userStore, musicStore} = useContext(Context);
    const [loading, setLoading] = useState(false);
    useEffect(() => {
        if(userStore.isAuth){
            setLoading(true)
            const loadFavorites = async () => {
                await musicStore.fetchFavTracks();
                setLoading(false);
            };
            loadFavorites();
        }
    }, [userStore.isAuth]);
    useEffect(() => {
        setLoading(true)
        const checkAuthentication = async () => {
            try {
                await userStore.checkAuth();
                setLoading(false);
            } catch (error) {
                console.error("Произошла ошибка при проверке авторизации:", error);
                setLoading(false);
            }
        };

        if (localStorage.getItem('token')) {
            checkAuthentication();
        } else {
            setLoading(false);
        }
    }, [userStore]);
    if(!loading){
        return (
            <div className='app'>
                <SnackbarProvider>
                    <AppRouter/>
                </SnackbarProvider>
            </div>
        );
    }
    return null

}

export default observer(App);
