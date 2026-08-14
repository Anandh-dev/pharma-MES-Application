import {
    Divider,
    Drawer,
    List,
    ListItemButton,
    ListItemIcon,
    ListItemText,
    Toolbar,
    Typography
} from "@mui/material";

import DashboardIcon from "@mui/icons-material/Dashboard";
import PrecisionManufacturingIcon from "@mui/icons-material/PrecisionManufacturing";
import AssignmentIcon from "@mui/icons-material/Assignment";
import InventoryIcon from "@mui/icons-material/Inventory";
import ScienceIcon from "@mui/icons-material/Science";
import BuildIcon from "@mui/icons-material/Build";
import AssessmentIcon from "@mui/icons-material/Assessment";
import NotificationsIcon from "@mui/icons-material/Notifications";

import { useNavigate } from "react-router-dom";

const drawerWidth = 240;

function Sidebar() {

    const navigate = useNavigate();

    const menuItems = [
        {
            label: "Dashboard",
            icon: <DashboardIcon />,
            path: "/dashboard"
        },
        {
            label: "Batches",
            icon: <InventoryIcon />,
            path: "/batches"
        },
        {
            label: "Production",
            icon: <PrecisionManufacturingIcon />,
            path: "/production"
        },
        {
            label: "Work Orders",
            icon: <AssignmentIcon />,
            path: "/work-orders"
        },
        {
            label: "Inventory",
            icon: <InventoryIcon />,
            path: "/inventory"
        },
        {
            label: "Quality",
            icon: <ScienceIcon />,
            path: "/quality"
        },
        {
            label: "Equipment",
            icon: <BuildIcon />,
            path: "/equipment"
        },
        {
            label: "Reports",
            icon: <AssessmentIcon />,
            path: "/reports"
        },
        {
            label: "Notifications",
            icon: <NotificationsIcon />,
            path: "/notifications"
        }
    ];

    return (
        <Drawer
            variant="permanent"
            sx={{
                width: drawerWidth,
                flexShrink: 0,
                "& .MuiDrawer-paper": {
                    width: drawerWidth,
                    boxSizing: "border-box"
                }
            }}
        >

            <Toolbar>

                <Typography
                    variant="h6"
                    fontWeight="bold"
                >
                    Pharma MES
                </Typography>

            </Toolbar>

            <Divider />

            <List>

                {menuItems.map((item) => (

                    <ListItemButton
                        key={item.label}
                        onClick={() => navigate(item.path)}
                    >

                        <ListItemIcon>
                            {item.icon}
                        </ListItemIcon>

                        <ListItemText
                            primary={item.label}
                        />

                    </ListItemButton>

                ))}

            </List>

        </Drawer>
    );
}

export default Sidebar;