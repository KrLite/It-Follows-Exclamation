package band.kessokuteatime.itfollows;

import net.fabricmc.api.ClientModInitializer;
import band.kessokuteatime.itfollows.mixin.ClickableWidgetAccessor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.OptionListWidget;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.util.Window;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector2d;
import org.joml.Vector2i;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class ItFollows implements ClientModInitializer {
	public static final String NAME = "It Follows!", ID = "itfollows";
	public static final Logger LOGGER = LoggerFactory.getLogger(ID);

	private static boolean followingAllowed = false;
	private static @Nullable ClickableWidget widget;
	private static final Vector2i widgetPos = new Vector2i();

	@Override
	public void onInitializeClient() {
	}

	private static boolean widgetCheckFailed(ClickableWidget widget) {
		return !widget.equals(ItFollows.widget);
	}

	public static void followingAllowed(ClickableWidget widget) {
		if (widgetCheckFailed(widget)) return;

		followingAllowed = true;
	}

	public static Optional<Vector2d> widgetPosUnscaled() {
		if (followingAllowed) {
			followingAllowed = false;

			int leftEdge = MinecraftClient.getInstance().getWindow().getScaledWidth() / 2 - 155;

			Window window = MinecraftClient.getInstance().getWindow();
			Vector2d pos = new Vector2d(
					window.getWidth() * (double)  (leftEdge + widgetPos.x()) / window.getScaledWidth(),
					window.getHeight() * (double) widgetPos.y() / window.getScaledHeight()
			);

			return Optional.ofNullable(
					pos.x() >= 0 && pos.x() <= window.getWidth() && pos.y() >= 0 && pos.y() <= window.getHeight() ? pos : null
			);
		} else return Optional.empty();
	}

	public static void foundWidget(ClickableWidget widget) {
		ItFollows.widget = widget;
	}

	public static void fetchXFromWidget(ClickableWidget widget) {
		if (widgetCheckFailed(widget)) return;

		ClickableWidgetAccessor accessor = ((ClickableWidgetAccessor) widget);
		widgetPos.set(accessor.getX() + accessor.getWidth() / 2, widgetPos.y());
	}

	public static void fetchYFromWidget(ClickableWidget widget) {
		if (widgetCheckFailed(widget)) return;

		ClickableWidgetAccessor accessor = ((ClickableWidgetAccessor) widget);
		widgetPos.set(widgetPos.x(), accessor.getY() + accessor.getHeight() / 2);
	}
}
