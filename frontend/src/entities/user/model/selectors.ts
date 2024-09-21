import {RootState} from "@/app/store.ts";
import {UserState} from "@/entities/user";
import {createSelector} from "@reduxjs/toolkit";

export const selectUserState = (state:RootState):UserState => state.user
export const selectUserLoading = createSelector([selectUserState],(userState) => userState.loading)

export const selectUserError = createSelector([selectUserState], (userState) => userState.error)

export const selectUser = createSelector([selectUserState], (userState) => userState.user)