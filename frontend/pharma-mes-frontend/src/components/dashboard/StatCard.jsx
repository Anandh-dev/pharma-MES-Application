import {
    Card,
    CardContent,
    Typography
} from "@mui/material";

function StatCard({
    title,
    value,
    subtitle
}) {

    return (
        <Card
            elevation={2}
            sx={{
                height: "100%"
            }}
        >

            <CardContent>

                <Typography
                    variant="body2"
                    color="text.secondary"
                >
                    {title}
                </Typography>

                <Typography
                    variant="h3"
                    fontWeight="bold"
                    sx={{ mt: 1 }}
                >
                    {value}
                </Typography>

                <Typography
                    variant="body2"
                    color="text.secondary"
                    sx={{ mt: 1 }}
                >
                    {subtitle}
                </Typography>

            </CardContent>

        </Card>
    );
}

export default StatCard;