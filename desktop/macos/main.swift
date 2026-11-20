import Cocoa
import WebKit

class AppDelegate: NSObject, NSApplicationDelegate, WKNavigationDelegate, WKUIDelegate {
    var window: NSWindow!
    var webView: WKWebView!

    func applicationDidFinishLaunching(_ notification: Notification) {
        setupMenu()
        setupWindow()
        loadMusic()
    }

    private func setupWindow() {
        let screenRect = NSScreen.main?.visibleFrame ?? NSRect(x: 0, y: 0, width: 1280, height: 820)
        let width: CGFloat = min(1280, screenRect.width * 0.9)
        let height: CGFloat = min(820, screenRect.height * 0.9)
        let frame = NSRect(
            x: (screenRect.width - width) / 2 + screenRect.origin.x,
            y: (screenRect.height - height) / 2 + screenRect.origin.y,
            width: width,
            height: height
        )

        window = NSWindow(
            contentRect: frame,
            styleMask: [.titled, .closable, .miniaturizable, .resizable, .fullSizeContentView],
            backing: .buffered,
            defer: false
        )
        window.title = "Aura Music"
        window.titleVisibility = .hidden
        window.titlebarAppearsTransparent = true
        window.isMovableByWindowBackground = true
        window.backgroundColor = NSColor(calibratedRed: 0.04, green: 0.04, blue: 0.04, alpha: 1.0)
        window.minSize = NSSize(width: 960, height: 620)

        // Configuration
        let config = WKWebViewConfiguration()
        config.allowsAirPlayForMediaPlayback = true
        config.mediaTypesRequiringUserActionForPlayback = []
        config.websiteDataStore = WKWebsiteDataStore.default()

        // Custom CSS for native macOS dark aesthetics
        let customCSS = """
            body, html {
                background-color: #080808 !important;
                font-family: -apple-system, BlinkMacSystemFont, "SF Pro Display", "SF Pro Text", "Helvetica Neue", sans-serif !important;
                -webkit-font-smoothing: antialiased;
            }
            ytmusic-nav-bar {
                background: rgba(15, 15, 15, 0.85) !important;
                backdrop-filter: blur(30px) saturate(180%) !important;
                -webkit-backdrop-filter: blur(30px) saturate(180%) !important;
                padding-left: 78px !important; /* Space for macOS traffic light buttons */
                border-bottom: 1px solid rgba(255, 255, 255, 0.07) !important;
            }
            #player-bar-background {
                background: rgba(18, 18, 18, 0.92) !important;
                backdrop-filter: blur(35px) saturate(190%) !important;
                -webkit-backdrop-filter: blur(35px) saturate(190%) !important;
                border-top: 1px solid rgba(255, 255, 255, 0.08) !important;
            }
            ytmusic-player-bar {
                padding: 0 24px !important;
            }
            /* Hide web promos and banners */
            ytmusic-mealbar-promo-renderer,
            .mealbar-promo-renderer,
            #premium-badge,
            ytmusic-banner-promo-renderer {
                display: none !important;
            }
            /* Polished scrollbars */
            ::-webkit-scrollbar {
                width: 8px;
                height: 8px;
            }
            ::-webkit-scrollbar-track {
                background: transparent;
            }
            ::-webkit-scrollbar-thumb {
                background: rgba(255, 255, 255, 0.15);
                border-radius: 4px;
            }
            ::-webkit-scrollbar-thumb:hover {
                background: rgba(255, 255, 255, 0.3);
            }
        """

        let jsCode = """
            (function() {
                var style = document.createElement('style');
                style.type = 'text/css';
                style.appendChild(document.createTextNode(`\(customCSS)`));
                document.head.appendChild(style);
            })();
        """

        let userScript = WKUserScript(source: jsCode, injectionTime: .atDocumentEnd, forMainFrameOnly: false)
        config.userContentController.addUserScript(userScript)

        webView = WKWebView(frame: window.contentView!.bounds, configuration: config)
        webView.autoresizingMask = [.width, .height]
        webView.navigationDelegate = self
        webView.uiDelegate = self
        webView.customUserAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.4 Safari/605.1.15"

        window.contentView?.addSubview(webView)
        window.makeKeyAndOrderFront(nil)
    }

    private func loadMusic() {
        if let url = URL(string: "https://music.youtube.com") {
            webView.load(URLRequest(url: url))
        }
    }

    // Playback control helpers
    @objc func playPause() {
        webView.evaluateJavaScript("document.querySelector('#play-pause-button')?.click()", completionHandler: nil)
    }

    @objc func nextTrack() {
        webView.evaluateJavaScript("document.querySelector('.next-button')?.click()", completionHandler: nil)
    }

    @objc func previousTrack() {
        webView.evaluateJavaScript("document.querySelector('.previous-button')?.click()", completionHandler: nil)
    }

    @objc func reloadPage() {
        webView.reload()
    }

    private func setupMenu() {
        let mainMenu = NSMenu()

        // Aura Music Menu
        let appMenuItem = NSMenuItem()
        mainMenu.addItem(appMenuItem)
        let appMenu = NSMenu()
        appMenu.addItem(withTitle: "About Aura Music", action: #selector(NSApplication.orderFrontStandardAboutPanel(_:)), keyEquivalent: "")
        appMenu.addItem(NSMenuItem.separator())
        appMenu.addItem(withTitle: "Hide Aura Music", action: #selector(NSApplication.hide(_:)), keyEquivalent: "h")
        let hideOthers = NSMenuItem(title: "Hide Others", action: #selector(NSApplication.hideOtherApplications(_:)), keyEquivalent: "h")
        hideOthers.keyEquivalentModifierMask = [.command, .option]
        appMenu.addItem(hideOthers)
        appMenu.addItem(withTitle: "Show All", action: #selector(NSApplication.unhideAllApplications(_:)), keyEquivalent: "")
        appMenu.addItem(NSMenuItem.separator())
        appMenu.addItem(withTitle: "Quit Aura Music", action: #selector(NSApplication.terminate(_:)), keyEquivalent: "q")
        appMenuItem.submenu = appMenu

        // Edit Menu
        let editMenuItem = NSMenuItem()
        mainMenu.addItem(editMenuItem)
        let editMenu = NSMenu(title: "Edit")
        editMenu.addItem(withTitle: "Undo", action: Selector(("undo:")), keyEquivalent: "z")
        editMenu.addItem(withTitle: "Redo", action: Selector(("redo:")), keyEquivalent: "Z")
        editMenu.addItem(NSMenuItem.separator())
        editMenu.addItem(withTitle: "Cut", action: Selector(("cut:")), keyEquivalent: "x")
        editMenu.addItem(withTitle: "Copy", action: Selector(("copy:")), keyEquivalent: "c")
        editMenu.addItem(withTitle: "Paste", action: Selector(("paste:")), keyEquivalent: "v")
        editMenu.addItem(withTitle: "Select All", action: Selector(("selectAll:")), keyEquivalent: "a")
        editMenuItem.submenu = editMenu

        // Controls Menu (Music shortcuts)
        let controlMenuItem = NSMenuItem()
        mainMenu.addItem(controlMenuItem)
        let controlMenu = NSMenu(title: "Playback")
        controlMenu.addItem(withTitle: "Play / Pause", action: #selector(playPause), keyEquivalent: " ")
        controlMenu.addItem(withTitle: "Next Track", action: #selector(nextTrack), keyEquivalent: "") // Cmd + Right arrow
        controlMenu.addItem(withTitle: "Previous Track", action: #selector(previousTrack), keyEquivalent: "") // Cmd + Left arrow
        controlMenuItem.submenu = controlMenu

        // View Menu
        let viewMenuItem = NSMenuItem()
        mainMenu.addItem(viewMenuItem)
        let viewMenu = NSMenu(title: "View")
        viewMenu.addItem(withTitle: "Reload Page", action: #selector(reloadPage), keyEquivalent: "r")
        viewMenuItem.submenu = viewMenu

        // Window Menu
        let windowMenuItem = NSMenuItem()
        mainMenu.addItem(windowMenuItem)
        let windowMenu = NSMenu(title: "Window")
        windowMenu.addItem(withTitle: "Minimize", action: #selector(NSWindow.miniaturize(_:)), keyEquivalent: "m")
        windowMenu.addItem(withTitle: "Zoom", action: #selector(NSWindow.zoom(_:)), keyEquivalent: "")
        windowMenuItem.submenu = windowMenu

        NSApplication.shared.mainMenu = mainMenu
    }

    func applicationShouldTerminateAfterLastWindowClosed(_ sender: NSApplication) -> Bool {
        return true
    }
}

let app = NSApplication.shared
let delegate = AppDelegate()
app.delegate = delegate
app.setActivationPolicy(.regular)
app.run()
