import {Navigate, Outlet} from "react-router-dom";
import {observer} from "mobx-react-lite";
import {useContext} from "react";
import {Context} from "@/main.tsx";
import {SIGN_IN} from "@/shared/consts";

export const PrivateRoute = observer(() => {
    const {userStore} = useContext(Context)
    if (userStore.isAuth) {
        return <Outlet/>
    } else {
        return <Navigate to={SIGN_IN} replace/>;
    }
});

