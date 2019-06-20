package omar.video.vrails
import grails.transaction.Transactional

@Transactional( readOnly = true )

class ScreenshotService {
    def grailsApplication

    def getScreenshot( def params) {
        def localScreenshotDir = grailsApplication.config.screenshot.localScreenshotDir

        // remove : for url purposes.  This, for whatever reason, gets converted to / when it gets
        // executed and creates problems with urls.
        //        params.timestamp = params.timestamp.replaceAll(':', '-')
//        def normalizedParams = params.timestamp.replaceAll(':', '-')

        // Expand this out to include name of video
        params.filePath = "${localScreenshotDir}/${params.timestamp}.jpg"

        def cmdScreenshot = [
                'ffmpeg',
                '-ss', params.timestamp,
                '-i', params.videoPath,
                '-vframes', '1',
                '-q:v', '2',
                "${localScreenshotDir}/${params.timestamp}.jpg"
        ]
        def proc = cmdScreenshot.execute()
        proc.consumeProcessOutput()

        [ params: params ]
    }
}
