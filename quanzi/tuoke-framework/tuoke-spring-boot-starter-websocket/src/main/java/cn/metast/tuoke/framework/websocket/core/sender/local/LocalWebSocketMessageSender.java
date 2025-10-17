package cn.metast.tuoke.framework.websocket.core.sender.local;

import cn.metast.tuoke.framework.websocket.core.sender.AbstractWebSocketMessageSender;
import cn.metast.tuoke.framework.websocket.core.sender.WebSocketMessageSender;
import cn.metast.tuoke.framework.websocket.core.session.WebSocketSessionManager;

/**
 * 本地的 {@link WebSocketMessageSender} 实现类
 *
 * 注意：仅仅适合单机场景！！！
 *
 * @author metast.cn
 */
public class LocalWebSocketMessageSender extends AbstractWebSocketMessageSender {

    public LocalWebSocketMessageSender(WebSocketSessionManager sessionManager) {
        super(sessionManager);
    }

}
