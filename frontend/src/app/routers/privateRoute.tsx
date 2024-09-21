import {Navigate, Outlet} from "react-router-dom";
import {observer} from "mobx-react-lite";
import {SIGN_IN} from "@/shared/consts";
import {useAppSelector} from "@/shared/lib/store";
import {selectUser} from "@/entities/user";

export const PrivateRoute = observer(() => {
    const user = useAppSelector(selectUser)
    if (user) {
        return <Outlet/>
    } else {
        return <Navigate to={SIGN_IN} replace/>;
    }
});

