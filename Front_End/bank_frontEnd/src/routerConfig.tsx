import { Login,Portal } from "./pages";
import React from "react";

export const RouteConfig = [
    {
        path:'/',
        element:<Login />
    },
    {
        path:'/Portal',
        element:<Portal />
    }
]