@echo off
setlocal enabledelayedexpansion

:: 目标目录（可以根据需要修改）
set "target_dir=C:\work\code\qrcode_file\output\qrcode\img"

:: 切换到目标目录
cd /d "%target_dir%"

:: 初始化计数器
set count=1

:: 遍历所有PNG文件并重命名
for %%f in (*.png) do (
    set "new_name=00000!count!.png"
    set "new_name=!new_name:~-10!
    ren "%%f" "!new_name!"
    set /a count+=1
)

echo "所有PNG文件已重新排序。"
pause
