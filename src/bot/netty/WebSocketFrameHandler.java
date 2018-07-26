/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package bot.netty;

import bot.Bot;
import bot.DataParser;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;

/**
 * Echoes uppercase content of text frames.
 */
public class WebSocketFrameHandler extends SimpleChannelInboundHandler<WebSocketFrame> {

	private DataParser dp = new DataParser();
	private Bot bot = null;
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		super.channelActive(ctx);
		System.out.println("channelActive !!!");
		bot = new Bot("0", dp);
		ctx.channel().writeAndFlush(new TextWebSocketFrame("BOT: "+bot.getMessage()));
	}
	
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame frame) throws Exception {
        // ping and pong frames already handled

        if (frame instanceof TextWebSocketFrame) {
            // Send the uppercase string back.
            String request = ((TextWebSocketFrame) frame).text();
            System.out.println(ctx.channel());
            System.out.println(request);
            
            // send the message to the bot and get the bot response
            String response = bot.send(request);

            // if the response is not empty display it
            if (response.length() > 0) {
                ctx.channel().writeAndFlush(new TextWebSocketFrame("BOT: "+response));
            }

            // display new state message
            ctx.channel().writeAndFlush(new TextWebSocketFrame("BOT: "+bot.getMessage()));

            // clear the message textbox
            ctx.channel().writeAndFlush(new TextWebSocketFrame("\n"));
            
        } else {
            String message = "unsupported frame type: " + frame.getClass().getName();
            throw new UnsupportedOperationException(message);
        }
    }
}
