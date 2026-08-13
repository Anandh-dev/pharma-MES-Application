import {
    Box,
    Toolbar
} from "@mui/material";

import Sidebar from "../components/navigation/Sidebar";
import Navbar from "../components/navigation/Navbar";

const drawerWidth = 240;

function MainLayout({ children }) {

    return (
        <Box sx={{ display: "flex" }}>

            <Navbar />

            <Sidebar />

            <Box
                component="main"
                sx={{
                    flexGrow: 1,
                    width: `calc(100% - ${drawerWidth}px)`,
                    minHeight: "100vh",
                    backgroundColor: "#f5f6fa",
                    p: 3
                }}
            >

                <Toolbar />

                {children}

            </Box>

        </Box>
    );
}

export default MainLayout;