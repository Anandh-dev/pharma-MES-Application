import {
    AppBar,
    Box,
    IconButton,
    Toolbar,
    Typography
} from "@mui/material";

import NotificationsIcon from "@mui/icons-material/Notifications";
import LogoutIcon from "@mui/icons-material/Logout";

import authService from "../../services/authService";

const drawerWidth = 240;

function Navbar() {

    const username =
        localStorage.getItem("username") || "User";

    const handleLogout = () => {

        authService.logout();

        window.location.href = "/login";
    };

    return (
        <AppBar
            position="fixed"
            sx={{
                width: `calc(100% - ${drawerWidth}px)`,
                ml: `${drawerWidth}px`
            }}
        >

            <Toolbar>

                <Typography
                    variant="h6"
                    sx={{ flexGrow: 1 }}
                >
                    Manufacturing Execution System
                </Typography>

                <Typography
                    sx={{ mr: 2 }}
                >
                    {username}
                </Typography>

                <IconButton color="inherit">
                    <NotificationsIcon />
                </IconButton>

                <IconButton
                    color="inherit"
                    onClick={handleLogout}
                >
                    <LogoutIcon />
                </IconButton>

            </Toolbar>

        </AppBar>
    );
}

export default Navbar;